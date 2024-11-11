package store.domain.payment.model;

import store.common.constants.ReceiptConstants;
import store.domain.inventory.model.Price;

import java.util.List;
import java.util.stream.Collectors;

public class Receipt {

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
        return ReceiptConstants.STORE_HEADER + ReceiptConstants.LINE_SEPARATOR +
                formatProductInfoHeader() +
                formatProductItems() +
                formatGiftInfo() +
                formatPriceSummary();
    }

    private String formatProductInfoHeader() {
        return String.format(ReceiptConstants.PRODUCT_INFO_HEADER_FORMAT,
                ReceiptConstants.PRODUCT_NAME_LABEL, ReceiptConstants.QUANTITY_LABEL, ReceiptConstants.PRICE_LABEL)
                + ReceiptConstants.LINE_SEPARATOR;
    }

    private String formatProductItems() {
        return items.stream()
                .map(this::formatProductItem)
                .collect(Collectors.joining());
    }

    private String formatProductItem(PaymentItem item) {
        Price itemTotalPrice = item.originPrice().multiply(item.quantity());
        return String.format(ReceiptConstants.ITEM_FORMAT, item.name(), item.quantity(), itemTotalPrice) + ReceiptConstants.LINE_SEPARATOR;
    }

    private String formatGiftInfo() {
        String giftItems = items.stream()
                .filter(item -> item.giftQuantity() > 0)
                .map(this::formatGiftItem)
                .collect(Collectors.joining());

        StringBuilder result = new StringBuilder();
        if (!giftItems.isEmpty()) {
            result.append(ReceiptConstants.GIFT_HEADER)
                    .append(ReceiptConstants.LINE_SEPARATOR)
                    .append(giftItems);
        }
        return result.toString();
    }

    private String formatGiftItem(PaymentItem item) {
        return String.format(ReceiptConstants.GIFT_FORMAT, item.name(), item.giftQuantity()) + ReceiptConstants.LINE_SEPARATOR;
    }

    private String formatPriceSummary() {
        int totalQuantity = items.stream()
                .mapToInt(PaymentItem::quantity)
                .sum();

        return ReceiptConstants.DIVIDER + ReceiptConstants.LINE_SEPARATOR
                + formatTotalAmount(totalQuantity)
                + formatDiscountInfo(ReceiptConstants.PROMOTION_DISCOUNT_LABEL, promotionDiscountPrice)
                + formatDiscountInfo(ReceiptConstants.MEMBERSHIP_DISCOUNT_LABEL, membershipDiscountPrice)
                + formatFinalPrice();
    }

    private String formatTotalAmount(int totalQuantity) {
        return String.format(ReceiptConstants.TOTAL_AMOUNT_FORMAT, ReceiptConstants.TOTAL_AMOUNT_LABEL, totalQuantity, totalPrice) + ReceiptConstants.LINE_SEPARATOR;
    }

    private String formatDiscountInfo(String label, Price discountPrice) {
        String discountValue = "";
        if (discountPrice != null) {
            discountValue = discountPrice.toString();
        }
        return String.format(ReceiptConstants.DISCOUNT_FORMAT, label, "", discountValue) + ReceiptConstants.LINE_SEPARATOR;
    }

    private String formatFinalPrice() {
        Price finalPrice = totalPrice;
        if (promotionDiscountPrice != null && membershipDiscountPrice != null) {
            finalPrice = totalPrice.subtract(promotionDiscountPrice.plus(membershipDiscountPrice));
        }
        if (promotionDiscountPrice != null && membershipDiscountPrice == null) {
            finalPrice = totalPrice.subtract(promotionDiscountPrice);
        }
        if (promotionDiscountPrice == null && membershipDiscountPrice != null) {
            finalPrice = totalPrice.subtract(membershipDiscountPrice);
        }
        return String.format(ReceiptConstants.FINAL_PRICE_FORMAT, ReceiptConstants.FINAL_PRICE_LABEL, "", finalPrice) + ReceiptConstants.LINE_SEPARATOR;
    }
}
