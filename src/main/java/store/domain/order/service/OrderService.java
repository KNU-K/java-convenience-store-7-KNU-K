package store.domain.order.service;

import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Price;
import store.domain.inventory.service.InventoryService;
import store.domain.order.model.OrderItem;
import store.domain.promotion.factory.PromotionStrategySelector;
import store.domain.promotion.model.PromotionPolicy;
import store.domain.promotion.service.PromotionService;
import store.domain.promotion.strategy.PromotionStrategy;

import java.util.List;

public class OrderService {
    private final InventoryService inventoryService;
    private final PromotionService promotionService;

    public OrderService(InventoryService inventoryService, PromotionService promotionService) {
        this.inventoryService = inventoryService;
        this.promotionService = promotionService;
    }

    public PromotionPolicy getPromotionPolicy(CartItem cartItem) {
        return promotionService.getPromotionPolicy(cartItem);
    }

    public Price calculateTotalCartPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(inventoryService::getTotalPriceOfEachItem)
                .reduce(Price.ZERO, Price::plus);
    }

    public Price findProductPrice(String productName) {
        return inventoryService.getProductPrice(productName);
    }

    public void checkSufficientQuantity(String name, int quantity) {
        inventoryService.checkSufficientQuantity(name, quantity);
    }

    public int getApplicableQuantity(CartItem cartItem) {
        PromotionPolicy policy = getPromotionPolicy(cartItem);
        return inventoryService.getApplicableQuantity(cartItem, policy);
    }

    public int getRemainingQuantity(CartItem cartItem) {
        return cartItem.quantity() - getApplicableQuantity(cartItem);
    }

    public boolean isPromotionQuantityValid(int availablePromotionQuantity, PromotionPolicy policy) {
        return availablePromotionQuantity > 0 && policy != null && policy.isValidPromotionQuantity(availablePromotionQuantity);
    }

    public void applyPromotionStrategy(CartItem cartItem, PromotionPolicy policy) {
        PromotionStrategy strategy = PromotionStrategySelector.select(cartItem, policy);
        strategy.apply(cartItem, policy);
    }
}
