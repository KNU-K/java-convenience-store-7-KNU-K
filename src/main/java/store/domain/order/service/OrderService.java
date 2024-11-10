package store.domain.order.service;

import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Price;
import store.domain.inventory.service.InventoryService;
import store.domain.order.model.OrderItem;
import store.domain.promotion.model.PromotionPolicy;
import store.domain.promotion.service.PromotionService;

import java.util.List;

public class OrderService {
    private final InventoryService inventoryService;
    private final PromotionService promotionService;

    public OrderService(InventoryService inventoryService, PromotionService promotionService) {
        this.inventoryService = inventoryService;
        this.promotionService = promotionService;
    }

    public int calculateAvailablePromotionQuantity(CartItem cartItem) {
        if (!isPromotionEligible(cartItem)) {
            return 0;
        }
        return getAvailablePromotionStock(cartItem.name());

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

    public int getAvailablePromotionStock(String productName) {
        return inventoryService.getPromotionStockQuantity(productName);
    }

    public boolean isPromotionEligible(CartItem item) {
        return promotionService.isPromotionApplicable(item);
    }

    public boolean isEligibleForExtraItem(CartItem item) {
        return inventoryService.isPromotionQuantitySufficient(item) && promotionService.isEligibleForExtraItem(item);
    }

    public void checkSufficientQuantity(String name, int quantity) {
        inventoryService.checkSufficientQuantity(name, quantity);
    }

    public int getApplicableQuantity(OrderItem orderItem) {
        return inventoryService.getApplicableQuantity(orderItem);
    }

    public boolean isValidPromotionQuantity(int promotionQuantity, PromotionPolicy promotionPolicy) {
        return promotionPolicy.isValidPromotionQuantity(promotionQuantity);
    }
}
