package store.domain.payment.factory;

import store.domain.inventory.model.Price;
import store.domain.payment.model.Payment;
import store.domain.payment.model.PaymentItem;
import store.domain.payment.model.Receipt;

import java.util.List;

public class ReceiptFactory {
    public static Receipt createReceipt(Payment payment) {
        List<PaymentItem> items = payment.paymentItems();
        Price totalPrice = payment.totalPrice();
        Price promotionDiscountPrice = payment.promotionDiscountPrice();
        Price membershipDiscountPrice = payment.membershipDiscountPrice();

        return new Receipt(items, totalPrice, promotionDiscountPrice, membershipDiscountPrice);
    }
}
