package store.domain.order.service;

import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Price;
import store.domain.inventory.service.InventoryService;
import store.domain.order.factory.OrderFactory;
import store.domain.order.model.OrderItem;
import store.domain.promotion.factory.PromotionStrategySelector;
import store.domain.promotion.model.PromotionPolicy;
import store.domain.promotion.service.PromotionService;
import store.domain.promotion.strategy.PromotionStrategy;
import store.domain.promotion.strategy.PromotionStrategyType;

import java.util.List;

public class OrderService {
    private final InventoryService inventoryService;
    private final PromotionService promotionService;
    private final PromotionConfirmCallback promotionCallback;

    public OrderService(InventoryService inventoryService, PromotionService promotionService, PromotionConfirmCallback promotionCallback) {
        this.inventoryService = inventoryService;
        this.promotionService = promotionService;
        this.promotionCallback = promotionCallback;
    }

    public OrderItem applyPromotion(CartItem cartItem) {
        PromotionPolicy policy = getPromotionPolicy(cartItem);
        Price price = inventoryService.getProductPrice(cartItem.name());
        PromotionStrategy strategy = PromotionStrategySelector.select(cartItem, policy);

        if (isValidPromotion(policy, strategy) && isExtraQuantityPromotion(strategy)) {
            return applyExtraPromotion(cartItem, price, policy, strategy);
        }
        if (isValidPromotion(policy, strategy) && isRegularPricePromotion(strategy)) {
            return applyRegularPromotion(cartItem, price, policy, strategy);
        }
        if (policy != null && policy.isValidDate()) {
            checkSufficientQuantity(cartItem.name(), cartItem.quantity());
            return createOrderItem(cartItem, price, getApplicableQuantity(cartItem), policy);
        }
        return createNoPromotionOrderItem(cartItem, price, policy);
    }

    public Price calculateTotalCartPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(inventoryService::getTotalPriceOfEachItem)
                .reduce(Price.ZERO, Price::plus);
    }

    public PromotionPolicy getPromotionPolicy(CartItem cartItem) {
        return promotionService.getPromotionPolicy(cartItem);
    }

    private OrderItem applyExtraPromotion(CartItem cartItem, Price price, PromotionPolicy policy, PromotionStrategy strategy) {
        int promotionQuantity = policy.calculateExtraPromotionQuantity(cartItem);
        if (!promotionCallback.confirmExtraPromotion(cartItem)) {
            return createOrderItem(cartItem, price, promotionQuantity - policy.getGetQuantity(), policy);
        }
        strategy.apply(cartItem, policy);

        return createOrderItem(cartItem, price, promotionQuantity, policy);
    }

    private OrderItem applyRegularPromotion(CartItem cartItem, Price price, PromotionPolicy policy, PromotionStrategy strategy) {
        int applicableQuantity = getApplicableQuantity(cartItem);
        int remainingQuantity = getRemainingQuantity(cartItem);

        if (!promotionCallback.confirmRegularPriceOption(cartItem, remainingQuantity)) {
            strategy.apply(cartItem, policy);
        }
        return createOrderItem(cartItem, price, applicableQuantity, policy);
    }

    private OrderItem createOrderItem(CartItem cartItem, Price price, int promotionQuantity, PromotionPolicy promotionPolicy) {
        return OrderFactory.createOrderItem(cartItem, price, promotionQuantity, promotionPolicy);
    }

    private OrderItem createNoPromotionOrderItem(CartItem cartItem, Price price, PromotionPolicy policy) {
        return OrderFactory.createOrderItem(cartItem, price, 0, policy);
    }

    private void checkSufficientQuantity(String name, int quantity) {
        inventoryService.checkSufficientQuantity(name, quantity);
    }

    private int getApplicableQuantity(CartItem cartItem) {
        PromotionPolicy policy = getPromotionPolicy(cartItem);
        return inventoryService.getApplicableQuantity(cartItem, policy);
    }

    private int getRemainingQuantity(CartItem cartItem) {
        return cartItem.quantity() - getApplicableQuantity(cartItem);
    }

    private boolean isValidPromotion(PromotionPolicy policy, PromotionStrategy strategy) {
        return policy != null && strategy != null;
    }

    private boolean isExtraQuantityPromotion(PromotionStrategy strategy) {
        return PromotionStrategySelector.toType(strategy) == PromotionStrategyType.EXTRA_QUANTITY;
    }

    private boolean isRegularPricePromotion(PromotionStrategy strategy) {
        return PromotionStrategySelector.toType(strategy) == PromotionStrategyType.REGULAR_PRICE_STRATEGY;
    }
}
