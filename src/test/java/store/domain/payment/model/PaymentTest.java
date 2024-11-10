package store.domain.payment.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.inventory.model.Price;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentTest {

    private Payment payment;
    private Price totalPrice;
    private Price membershipDiscountPrice;
    private Price promotionDiscountPrice;
    private PaymentItem paymentItem1;
    private PaymentItem paymentItem2;

    @BeforeEach
    void setUp() {
        totalPrice = new Price(10000);
        membershipDiscountPrice = new Price(1000);
        promotionDiscountPrice = new Price(500);

        paymentItem1 = new PaymentItem("상품1", 2, 1, new Price(2000), new Price(200));
        paymentItem2 = new PaymentItem("상품2", 3, 0, new Price(1000), new Price(100));

        List<PaymentItem> paymentItems = Arrays.asList(paymentItem1, paymentItem2);
        payment = new Payment(paymentItems, totalPrice, membershipDiscountPrice, promotionDiscountPrice);
    }

    @Test
    void 결제_생성() {
        assertNotNull(payment);
        assertEquals(2, payment.paymentItems().size());
        assertEquals(totalPrice, payment.totalPrice());
        assertEquals(membershipDiscountPrice, payment.membershipDiscountPrice());
        assertEquals(promotionDiscountPrice, payment.promotionDiscountPrice());
    }

    @Test
    void paymentItems() {
        List<PaymentItem> items = payment.paymentItems();
        assertEquals(2, items.size());
        assertEquals("상품1", items.get(0).name());
        assertEquals("상품2", items.get(1).name());
    }

    @Test
    void totalPrice() {
        assertEquals(totalPrice, payment.totalPrice());
    }

    @Test
    void membershipDiscountPrice() {
        assertEquals(membershipDiscountPrice, payment.membershipDiscountPrice());
    }

    @Test
    void promotionDiscountPrice() {
        assertEquals(promotionDiscountPrice, payment.promotionDiscountPrice());
    }
}
