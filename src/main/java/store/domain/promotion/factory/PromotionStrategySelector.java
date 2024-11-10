package store.domain.promotion.factory;

import store.common.initializer.InventoryInitializer;
import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Inventory;
import store.domain.promotion.model.PromotionPolicy;
import store.domain.promotion.strategy.ExtraQuantityStrategy;
import store.domain.promotion.strategy.PromotionStrategy;
import store.domain.promotion.strategy.PromotionStrategyType;
import store.domain.promotion.strategy.RegularPriceStrategy;

import java.util.HashMap;
import java.util.Map;

public class PromotionStrategySelector {
    private static final Map<PromotionStrategyType, PromotionStrategy> promotionStrategyMap = new HashMap<>();

    static {
        promotionStrategyMap.put(PromotionStrategyType.EXTRA_QUANTITY, new ExtraQuantityStrategy());
        promotionStrategyMap.put(PromotionStrategyType.REGULAR_PRICE_STRATEGY, new RegularPriceStrategy());
    }

    public static PromotionStrategyType toType(PromotionStrategy promotionStrategy) {
        return promotionStrategyMap.entrySet().stream()
                .filter(entry -> entry.getValue().getClass().equals(promotionStrategy.getClass()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    public static PromotionStrategy select(CartItem cartItem, PromotionPolicy policy) {
        if (policy == null||!policy.isValidDate()) {
            return null;
        }
        if (determineExtraQuantity(cartItem, policy)) {
            return promotionStrategyMap.get(PromotionStrategyType.EXTRA_QUANTITY);
        }

        if (determineRegularPriceStrategy(cartItem, policy)) {
            return promotionStrategyMap.get(PromotionStrategyType.REGULAR_PRICE_STRATEGY);
        }

        return null;
    }

    private static boolean determineExtraQuantity(CartItem cartItem, PromotionPolicy policy) {
        int availablePromotionQuantity = policy.calculateOptimalPromotionQuantity(cartItem);
        return availablePromotionQuantity > cartItem.quantity();
    }

    private static boolean determineRegularPriceStrategy(CartItem cartItem, PromotionPolicy policy) {
        Inventory inventory = InventoryInitializer.getInstance().getInventory();
        return policy.getApplicableQuantity(cartItem.quantity()) > inventory.getPromotionStockCount(cartItem.name());
    }
}
