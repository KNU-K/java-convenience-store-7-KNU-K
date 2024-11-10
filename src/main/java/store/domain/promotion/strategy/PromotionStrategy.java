package store.domain.promotion.strategy;

import store.domain.inventory.model.CartItem;
import store.domain.promotion.model.PromotionPolicy;

public interface PromotionStrategy {
    void apply(CartItem cartItem, PromotionPolicy policy);
}
