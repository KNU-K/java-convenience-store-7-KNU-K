package store.domain.promotion.service;

import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Inventory;
import store.domain.inventory.model.PromotionStock;
import store.domain.promotion.model.PromotionPolicy;

import java.util.Optional;

public class PromotionService {
    private final Inventory inventory;

    public PromotionService(Inventory inventory) {
        this.inventory = inventory;
    }


    public PromotionPolicy getPromotionPolicy(CartItem item) {
        return getPromotionStock(item)
                .map(promotionStock -> promotionStock.getPromotion().policy())
                .orElse(null);
    }

    public boolean isPromotionApplicable(CartItem item) {
        return getPromotionStock(item)
                .map(PromotionStock::isValidPromotionDate)
                .orElse(false);
    }

    public boolean isEligibleForExtraItem(CartItem item) {
        return getPromotionStock(item)
                .map(stock -> stock.getPromotion().isEligibleForExtraItem(item.quantity()))
                .orElse(false);
    }

    private Optional<PromotionStock> getPromotionStock(CartItem item) {
        return inventory.streamPromotionStock()
                .filter(promotionItem -> promotionItem.isProductNameEqual(item.name()))
                .findFirst();
    }
}
