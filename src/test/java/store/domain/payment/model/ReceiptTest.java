package store.domain.payment.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.inventory.model.Price;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReceiptTest {

    private Price totalPrice;
    private Price promotionDiscountPrice;
    private Price membershipDiscountPrice;
    private PaymentItem paymentItem1;
    private PaymentItem paymentItem2;
    private Receipt receipt;

    @BeforeEach
    void setUp() {
        totalPrice = new Price(5000);
        promotionDiscountPrice = new Price(500);
        membershipDiscountPrice = new Price(200);

        paymentItem1 = new PaymentItem("상품1", 2, 1, new Price(2000), new Price(200));
        paymentItem2 = new PaymentItem("상품2", 3, 0, new Price(1000), new Price(100));

        List<PaymentItem> paymentItems = Arrays.asList(paymentItem1, paymentItem2);
        receipt = new Receipt(paymentItems, totalPrice, promotionDiscountPrice, membershipDiscountPrice);
    }

    @Test
    void 영수증_포맷팅_확인() {
        String receiptString = receipt.toString();
        assertNotNull(receiptString);
        assertTrue(receiptString.contains("============== W 편의점 ==============="));
        assertTrue(receiptString.contains("상품명"));
        assertTrue(receiptString.contains("수량"));
        assertTrue(receiptString.contains("금액"));
        assertTrue(receiptString.contains("총구매액"));
        assertTrue(receiptString.contains("행사할인"));
        assertTrue(receiptString.contains("멤버십할인"));
        assertTrue(receiptString.contains("내실돈"));
    }

    @Test
    void 영수증_최종_형식() {
        String receiptString = receipt.toString();
        assertTrue(receiptString.contains("============== W 편의점 ==============="));
        assertTrue(receiptString.contains("상품명"));
        assertTrue(receiptString.contains("수량"));
        assertTrue(receiptString.contains("금액"));
        assertTrue(receiptString.contains("총구매액"));
        assertTrue(receiptString.contains("행사할인"));
        assertTrue(receiptString.contains("멤버십할인"));
        assertTrue(receiptString.contains("내실돈"));
        assertTrue(receiptString.contains("상품1"));
        assertTrue(receiptString.contains("상품2"));
    }
}
