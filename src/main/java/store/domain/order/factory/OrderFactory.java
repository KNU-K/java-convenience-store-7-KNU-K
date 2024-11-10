package store.domain.order.factory;

import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Price;
import store.domain.order.model.OrderItem;
import store.domain.promotion.model.PromotionPolicy;

public class OrderFactory {
    public static OrderItem createOrderItem(CartItem cartItem, Price price, int quantity, PromotionPolicy policy) {
        return new OrderItem(cartItem, price, quantity, policy);
    }
}
