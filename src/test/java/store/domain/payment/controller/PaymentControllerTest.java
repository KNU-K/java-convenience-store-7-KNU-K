package store.domain.payment.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Price;
import store.domain.order.factory.OrderFactory;
import store.domain.order.model.Order;
import store.mock.MockInputView;
import store.mock.MockInventoryService;
import store.mock.MockOutputView;
import store.mock.MockPaymentService;


import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentControllerTest {
    private PaymentController paymentController;
    private MockInputView inputView;
    private MockOutputView outputView;
    private MockPaymentService paymentService;
    private MockInventoryService inventoryService;

    @BeforeEach
    void 초기화() {
        inputView = new MockInputView(null);
        outputView = new MockOutputView();
        paymentService = new MockPaymentService();
        inventoryService = new MockInventoryService();
        paymentController = new PaymentController(inputView, outputView, paymentService, inventoryService);
    }

    @Test
    void 결제_성공시_재고감소와_영수증출력() {
        // Given
        Order order = new Order(Collections.singletonList(OrderFactory.createOrderItem(new CartItem("콜라", 3), Price.of(3000), 0, null)), Price.of(3000)); // 필요한 필드 초기화
        inputView.setMembership(true);

        // When
        paymentController.doPayment(order);

        // Then
        assertTrue(paymentService.isProcessPaymentCalled());
        assertTrue(outputView.isMessageDisplayed());
    }

    @Test
    void 결제후_추가구매_확인() {
        inputView.setContinueShopping(true);
        boolean result = paymentController.askForAdditionalPurchaseAfterPayment();
        assertTrue(result);
    }
}
