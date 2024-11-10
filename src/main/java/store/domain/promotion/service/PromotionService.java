package store.domain.promotion.service;

import store.common.initializer.InventoryInitializer;
import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Inventory;
import store.domain.promotion.model.PromotionPolicy;

public class PromotionService {

    public PromotionPolicy getPromotionPolicy(CartItem item) {
        Inventory inventory = InventoryInitializer.getInstance().getInventory();
        return inventory.streamPromotionStock()
                .filter(promotionItem -> promotionItem.isProductNameEqual(item.name()))
                .findFirst()
                .map(promotionStock -> promotionStock.getPromotion().policy())
                .orElse(null);
    }

}
