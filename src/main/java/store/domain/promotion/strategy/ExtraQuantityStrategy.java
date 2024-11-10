package store.domain.promotion.strategy;

import store.domain.inventory.model.CartItem;
import store.domain.promotion.model.PromotionPolicy;

public class ExtraQuantityStrategy implements PromotionStrategy {
    @Override
    public void apply(CartItem cartItem, PromotionPolicy policy) {
        cartItem.increaseQuantity();
    }
}
