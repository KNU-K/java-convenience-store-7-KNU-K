package store.domain.payment.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.inventory.model.Price;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentItemTest {

    private PaymentItem paymentItem;
    private Price originPrice;
    private Price promotionDiscountPrice;

    @BeforeEach
    void setUp() {
        originPrice = new Price(2000);
        promotionDiscountPrice = new Price(200);
        paymentItem = new PaymentItem("상품1", 2, 1, originPrice, promotionDiscountPrice);
    }

    @Test
    void paymentItem_생성() {
        assertNotNull(paymentItem);
        assertEquals("상품1", paymentItem.name());
        assertEquals(2, paymentItem.quantity());
        assertEquals(1, paymentItem.giftQuantity());
        assertEquals(originPrice, paymentItem.originPrice());
        assertEquals(promotionDiscountPrice, paymentItem.promotionDiscountPrice());
    }

    @Test
    void quantity() {
        assertEquals(2, paymentItem.quantity());
    }

    @Test
    void giftQuantity() {
        assertEquals(1, paymentItem.giftQuantity());
    }

    @Test
    void originPrice() {
        assertEquals(originPrice, paymentItem.originPrice());
    }

    @Test
    void promotionDiscountPrice() {
        assertEquals(promotionDiscountPrice, paymentItem.promotionDiscountPrice());
    }
}
