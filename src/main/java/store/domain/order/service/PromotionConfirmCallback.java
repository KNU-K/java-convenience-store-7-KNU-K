package store.domain.order.service;

import store.domain.inventory.model.CartItem;

public interface PromotionConfirmCallback {
    boolean confirmExtraPromotion(CartItem cartItem);

    boolean confirmRegularPriceOption(CartItem cartItem, int remainingQuantity);
}
