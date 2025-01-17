package store.domain.inventory.service;

import store.common.constants.ErrorMessages;
import store.common.exception.InvalidInventoryException;
import store.domain.inventory.model.*;
import store.domain.order.model.Order;
import store.domain.order.model.OrderItem;
import store.domain.promotion.model.PromotionPolicy;

import java.util.Optional;

public class InventoryService {
    private static final Price DEFAULT_PRICE = Price.ZERO;

    private final Inventory inventory;

    public InventoryService(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getInventoryStatus() {
        return inventory.toString();
    }

    public void decreaseStockOfItem(Order order) {
        if (order.totalPrice().equals(Price.ZERO)) {
            throw new InvalidInventoryException(ErrorMessages.EXCEEDS_STOCK);
        }

        order.forEachItems(orderItem -> {
            if (orderItem.promotionPolicy() != null) {
                inventory.decreasePromotionStock(orderItem.name(), orderItem.promotionQuantity());
                inventory.decreaseStock(orderItem.name(), orderItem.quantity() - orderItem.promotionQuantity());
            } else {
                inventory.decreaseStock(orderItem.name(), orderItem.quantity());
            }
        });
    }

    public int getApplicableQuantity(CartItem cartItem, PromotionPolicy policy) {
        if (policy == null) {
            return 0;
        }
        int totalQuantity = getPromotionStockQuantity(cartItem.name());
        if (totalQuantity < cartItem.quantity()) {
            return policy.getApplicableQuantity(totalQuantity);
        }
        return policy.getApplicableQuantity(cartItem.quantity());
    }


    public Price getProductPrice(String productName) {
        return getStockItemPrice(productName)
                .orElseGet(() -> getPromotionStockPrice(productName));
    }

    public Price getTotalPriceOfEachItem(OrderItem item) {
        return getStockItemPrice(item.name())
                .orElse(DEFAULT_PRICE)
                .multiply(item.quantity());
    }

    public void checkSufficientQuantity(String productName, int requiredQuantity) {
        int totalQuantity = getStock(productName)
                .map(Stock::getQuantity)
                .orElse(0) + getPromotionStockQuantity(productName);
        if (totalQuantity < requiredQuantity) {
            throw new InvalidInventoryException(ErrorMessages.EXCEEDS_STOCK);
        }
    }

    private Optional<Stock> getStock(String productName) {
        return inventory.streamStock()
                .filter(item -> item.isProductNameEqual(productName))
                .findFirst();
    }

    private Optional<Price> getStockItemPrice(String productName) {
        return inventory.streamStock()
                .filter(item -> item.isProductNameEqual(productName))
                .findFirst()
                .map(stock -> stock.getProduct().price());
    }

    private Price getPromotionStockPrice(String productName) {
        return inventory.streamPromotionStock()
                .filter(item -> item.isProductNameEqual(productName))
                .findFirst()
                .map(stock -> stock.getProduct().price())
                .orElse(DEFAULT_PRICE);
    }

    public int getPromotionStockQuantity(String productName) {
        return inventory.streamPromotionStock()
                .filter(item -> item.isProductNameEqual(productName))
                .findFirst()
                .map(PromotionStock::getQuantity)
                .orElse(0);
    }

}
