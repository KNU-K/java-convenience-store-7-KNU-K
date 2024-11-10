package store.domain.payment.service;

import store.domain.inventory.model.Price;
import store.domain.order.model.Order;
import store.domain.order.model.OrderItem;
import store.domain.payment.model.Payment;
import store.domain.payment.model.PaymentItem;
import store.domain.promotion.model.PromotionPolicy;

import java.util.List;

public class PaymentService {

    private static final int MEMBERSHIP_DISCOUNT_PERCENTAGE = 30;
    private static final Price NO_DISCOUNT = Price.ZERO;
    private static final Price MAX_MEMBERSHIP_DISCOUNT = Price.of(8000);

    public Payment processPayment(Order order, boolean isMembership) {
        List<PaymentItem> paymentItems = createPaymentItemsFromOrder(order);
        Price totalGiftDiscount = calculateTotalGiftDiscount(order);

        if (!isMembership) {
            return Payment.create(paymentItems, order.totalPrice(), NO_DISCOUNT, totalGiftDiscount);
        }

        Price membershipDiscount = calculateDiscountForMembership(order);
        if (membershipDiscount.isBiggerThan(MAX_MEMBERSHIP_DISCOUNT)) {
            membershipDiscount = MAX_MEMBERSHIP_DISCOUNT;
        }

        return Payment.create(paymentItems, order.totalPrice(), membershipDiscount, totalGiftDiscount);
    }

    private List<PaymentItem> createPaymentItemsFromOrder(Order order) {
        return order.streamOrderItems()
                .map(this::createPaymentItemFromOrderItem)
                .toList();
    }

    private PaymentItem createPaymentItemFromOrderItem(OrderItem orderItem) {
        int giftItemsCount = calculateGiftItemsCount(orderItem);
        Price giftDiscount = calculateGiftDiscount(orderItem);
        return PaymentItem.create(orderItem.name(), orderItem.quantity(), giftItemsCount, orderItem.price(), giftDiscount);
    }

    private int calculateGiftItemsCount(OrderItem orderItem) {
        PromotionPolicy promotionPolicy = orderItem.promotionPolicy();
        if (promotionPolicy != null) {
            return promotionPolicy.getGiftQuantity(orderItem.promotionQuantity());
        }
        return 0;
    }

    private Price calculateGiftDiscount(OrderItem orderItem) {
        int giftItemsCount = calculateGiftItemsCount(orderItem);
        return orderItem.price().multiply(giftItemsCount);
    }

    private Price calculateTotalGiftDiscount(Order order) {
        return order.streamOrderItems()
                .map(this::calculateGiftDiscount)
                .reduce(NO_DISCOUNT, Price::plus);
    }

    private Price calculateEachGiftItemPrice(OrderItem orderItem) {
        if (orderItem.promotionPolicy() == null) {
            return Price.of(0);
        }
        int quantity = orderItem.promotionQuantity();
        return orderItem.price().multiply(quantity);
    }

    private Price calculateTotalGiftItemsPrice(Order order) {
        return order.streamOrderItems()
                .map(this::calculateEachGiftItemPrice)
                .reduce(NO_DISCOUNT, Price::plus);
    }

    private Price calculateDiscountForMembership(Order order) {
        Price totalGiftItemsPrice = calculateTotalGiftItemsPrice(order);
        return order.totalPrice()
                .subtract(totalGiftItemsPrice)
                .applyPercentage(MEMBERSHIP_DISCOUNT_PERCENTAGE);
    }
}