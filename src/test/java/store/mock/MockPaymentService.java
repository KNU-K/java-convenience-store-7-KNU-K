package store.mock;

import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Price;
import store.domain.order.factory.OrderFactory;
import store.domain.order.model.Order;
import store.domain.payment.model.Payment;
import store.domain.payment.model.PaymentItem;
import store.domain.payment.service.PaymentService;

import java.util.Collections;
import java.util.List;

public class MockPaymentService extends PaymentService {
    private boolean processPaymentCalled = false;

    @Override
    public Payment processPayment(Order order, boolean isMembership) {
        processPaymentCalled = true;
        new Order(Collections.singletonList(OrderFactory.createOrderItem(new CartItem("콜라", 3), Price.of(3000), 0, null)), Price.of(3000));

        return new Payment(List.of(new PaymentItem("콜라", 3, 0, Price.of(3000), Price.ZERO)), Price.of(3000), Price.ZERO, Price.ZERO);
    }

    public boolean isProcessPaymentCalled() {
        return processPaymentCalled;
    }
}

