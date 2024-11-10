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

    public int getApplicableQuantity(CartItem cartItem) {
        PromotionPolicy policy = getPromotionPolicy(cartItem);
        return inventoryService.getApplicableQuantity(cartItem, policy);
    }

    public boolean isValidPromotionQuantity(int promotionQuantity, PromotionPolicy promotionPolicy) {
        return promotionPolicy.isValidPromotionQuantity(promotionQuantity);
    }

    public int calculateLimitedPromotionQuantity(CartItem cartItem, PromotionPolicy policy) {
        if(policy == null) return 0;
        return policy.getApplicableQuantity(cartItem.quantity());
    }

    public int getRemainingQuantity(CartItem cartItem) {
        return cartItem.quantity() - getApplicableQuantity(cartItem);
    }
    public int calculateAvailablePromotionQuantity(PromotionPolicy policy, CartItem cartItem) {
        return policy.calculateOptimalPromotionQuantity(cartItem);
    }

    public boolean isPromotionQuantityValid(int availablePromotionQuantity, PromotionPolicy policy) {
        return availablePromotionQuantity > 0 && policy != null && policy.isValidPromotionQuantity(availablePromotionQuantity);
    }
    public OrderItem createOrderItemWithPromotion(CartItem cartItem, PromotionPolicy policy) {
        int availablePromotionQuantity = policy.calculateOptimalPromotionQuantity(cartItem);
        if (availablePromotionQuantity == 0) {
            return new OrderItem(cartItem, findProductPrice(cartItem.name()), 0, policy);
        }
        if (cartItem.quantity() == availablePromotionQuantity) {
            return new OrderItem(cartItem,  findProductPrice(cartItem.name()), policy.calculateOptimalPromotionQuantity(cartItem), policy);
        }
        if (!isPromotionQuantityValid(availablePromotionQuantity, policy)) {
            return new OrderItem(cartItem,  findProductPrice(cartItem.name()), 0, policy);
        }
        return null;
    }
}
