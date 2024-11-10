package store.domain.payment.model;

import store.domain.inventory.model.Price;

import java.util.List;

/**
 * @param paymentItems            결제 항목 리스트
 * @param totalPrice              결제 총액
 * @param membershipDiscountPrice 할인 총액
 * @param promotionDiscountPrice
 */
public record Payment(List<PaymentItem> paymentItems, Price totalPrice, Price membershipDiscountPrice,
                      Price promotionDiscountPrice) {
    public static Payment create(List<PaymentItem> paymentItems, Price totalPrice, Price membershipDiscount, Price totalGiftDiscount) {
        return new Payment(paymentItems, totalPrice, membershipDiscount, totalGiftDiscount);
    }
}
