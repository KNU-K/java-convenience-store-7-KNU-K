package store.domain.promotion.strategy;

import store.common.initializer.InventoryInitializer;
import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Inventory;
import store.domain.promotion.model.PromotionPolicy;

public class RegularPriceStrategy implements PromotionStrategy {
    @Override
    public void apply(CartItem cartItem, PromotionPolicy policy) {
        Inventory inventory = InventoryInitializer.getInstance().getInventory();
        int applicableQuantity = inventory.getApplicableQuantity(cartItem, policy);
        int remainingQuantity = cartItem.quantity() - applicableQuantity;

        cartItem.decreaseQuantity(remainingQuantity);
    }
}
