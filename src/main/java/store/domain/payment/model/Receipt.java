package store.domain.payment.model;

import store.domain.inventory.model.Price;

import java.util.List;

public class Receipt {

    private static final String STORE_HEADER = "============== W 편의점 ===============";
    private static final String PRODUCT_INFO_HEADER_FORMAT = "%-15s%-10s%-10s";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String DIVIDER = "====================================";
    private static final String PRODUCT_NAME_LABEL = "상품명";
    private static final String QUANTITY_LABEL = "수량";
    private static final String PRICE_LABEL = "금액";
    private static final String GIFT_HEADER = "============= 증 정 ===============";
    private static final String TOTAL_AMOUNT_LABEL = "총구매액";
    private static final String PROMOTION_DISCOUNT_LABEL = "행사할인";
    private static final String MEMBERSHIP_DISCOUNT_LABEL = "멤버십할인";
    private static final String FINAL_PRICE_LABEL = "내실돈";
    private static final String ITEM_FORMAT = "%-15s%-10d%-10s";
    private static final String GIFT_FORMAT = "%-15s%-10d";
    private static final String DISCOUNT_FORMAT = "%-25s%-10s%-10s";
    private static final String FINAL_PRICE_FORMAT = "%-25s%-10s%-10s";

    private final List<PaymentItem> items;
    private final Price totalPrice;
    private final Price promotionDiscountPrice;
    private final Price membershipDiscountPrice;

    public Receipt(List<PaymentItem> items, Price totalPrice, Price promotionDiscountPrice, Price membershipDiscountPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
        this.promotionDiscountPrice = promotionDiscountPrice;
        this.membershipDiscountPrice = membershipDiscountPrice;
    }

    @Override
    public String toString() {

        return STORE_HEADER + LINE_SEPARATOR +
                formatProductInfoHeader() +
                formatProductItems() +
                formatGiftInfo() +
                formatPriceSummary();
    }

    private String formatProductInfoHeader() {
        return String.format(PRODUCT_INFO_HEADER_FORMAT, PRODUCT_NAME_LABEL, QUANTITY_LABEL, PRICE_LABEL) + LINE_SEPARATOR;
    }

    private String formatProductItems() {
        String productItems = items.stream()
                .map(this::formatProductItem)
                .reduce("", (acc, itemString) -> acc + itemString);

        int totalQuantity = items.stream()
                .mapToInt(PaymentItem::quantity)
                .sum();

        return productItems + formatTotalAmount(totalQuantity);
    }

    private String formatProductItem(PaymentItem item) {
        return String.format(ITEM_FORMAT, item.name(), item.quantity(), item.originPrice().multiply(item.quantity())) + LINE_SEPARATOR;
    }

    private String formatGiftInfo() {
        String giftItems = items.stream()
                .filter(item -> item.giftQuantity() > 0)
                .map(this::formatGiftItem)
                .reduce("", (acc, itemString) -> acc + itemString);

        return GIFT_HEADER + LINE_SEPARATOR + giftItems;
    }


    private String formatGiftItem(PaymentItem item) {
        return String.format(GIFT_FORMAT, item.name(), item.giftQuantity()) + LINE_SEPARATOR;
    }

    private String formatPriceSummary() {
        return DIVIDER + LINE_SEPARATOR
                + formatDiscountInfo(PROMOTION_DISCOUNT_LABEL, promotionDiscountPrice)
                + formatDiscountInfo(MEMBERSHIP_DISCOUNT_LABEL, membershipDiscountPrice)
                + formatFinalPrice();
    }

    private String formatTotalAmount(int totalQuantity) {
        return String.format(DISCOUNT_FORMAT, TOTAL_AMOUNT_LABEL, totalQuantity, totalPrice) + LINE_SEPARATOR;
    }

    private String formatDiscountInfo(String label, Price discountPrice) {
        return String.format(DISCOUNT_FORMAT, label, "", discountPrice) + LINE_SEPARATOR;
    }

    private String formatFinalPrice() {
        Price finalPrice = totalPrice.subtract(promotionDiscountPrice.plus(membershipDiscountPrice));
        return String.format(FINAL_PRICE_FORMAT, FINAL_PRICE_LABEL, "", finalPrice) + LINE_SEPARATOR;
    }
}
