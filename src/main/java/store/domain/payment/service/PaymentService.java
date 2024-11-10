package store.domain.payment.service;

import store.domain.inventory.model.Price;
import store.domain.order.model.Order;
import store.domain.order.model.OrderItem;
import store.domain.payment.model.Payment;
import store.domain.payment.model.PaymentItem;
import store.domain.promotion.model.PromotionPolicy;

import java.util.List;
import java.util.function.Function;

public class PaymentService {

    private static final int MEMBERSHIP_DISCOUNT_PERCENTAGE = 30;
    private static final Price NO_DISCOUNT = Price.ZERO;

    public Payment processPayment(Order order, boolean isMembership) {
        List<PaymentItem> paymentItems = order.streamOrderItems().map(processPaymentForEachItem()).toList();

        Price totalGiftDiscount = calculateTotalGiftDiscount(order);

        if (!isMembership) {
            return new Payment(paymentItems, order.totalPrice(), NO_DISCOUNT, totalGiftDiscount);
        }
        Price membershipDiscount = calculateDiscountForMembership(order);
        return new Payment(paymentItems, order.totalPrice(), membershipDiscount, totalGiftDiscount);
    }

    private Function<OrderItem, PaymentItem> processPaymentForEachItem() {
        return orderItem -> new PaymentItem(orderItem.name(), orderItem.quantity(), calculateGiftItemsCount(orderItem), orderItem.price(), calculateGiftDiscount(orderItem));
    }

    private int calculateGiftItemsCount(OrderItem orderItem) {
        PromotionPolicy promotionPolicy = orderItem.promotionPolicy();
        if (promotionPolicy != null) {
            return promotionPolicy.getGiftQuantity(orderItem.promotionQuantity());
        }
        return 0;
    }

    private Price calculateGiftDiscount(OrderItem orderItem) {
        return orderItem.price().multiply(calculateGiftItemsCount(orderItem));
    }

    private Price calculateTotalGiftDiscount(Order order) {
        return order.streamOrderItems().map(this::calculateGiftDiscount).reduce(NO_DISCOUNT, Price::plus);
    }

    private Price calculateEachGiftItemPrice(OrderItem orderItem) {
        if (orderItem.promotionPolicy() == null) {
            return Price.of(0);
        }
        int quantity = orderItem.promotionQuantity();
        return orderItem.price().multiply(quantity);
    }

    private Price calculateTotalGiftItemsPrice(Order order) {
        return order.streamOrderItems().map(this::calculateEachGiftItemPrice)
                .reduce(NO_DISCOUNT, Price::plus);
    }

    private Price calculateDiscountForMembership(Order order) {
        return order.totalPrice()
                .subtract(calculateTotalGiftItemsPrice(order))
                .applyPercentage(MEMBERSHIP_DISCOUNT_PERCENTAGE);
    }
}
