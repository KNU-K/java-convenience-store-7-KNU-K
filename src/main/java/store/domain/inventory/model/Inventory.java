package store.domain.inventory.model;


import store.domain.promotion.model.Promotion;
import store.domain.promotion.model.PromotionPolicy;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Inventory {
    private static final int INIT_SEQUENCE_ID = 1;
    private final Map<Integer, Stock> stocks;
    private final Map<Integer, PromotionStock> promotionStocks;

    public Inventory() {
        this.stocks = new HashMap<>();
        this.promotionStocks = new HashMap<>();
    }

    private Integer getCurrentSequenceId() {
        return INIT_SEQUENCE_ID + stocks.size() + promotionStocks.size();
    }

    public void addStock(Stock stock) {
        if (!increaseStockIfExists(stock)) {
            Integer sequenceId = getCurrentSequenceId();
            stocks.put(sequenceId, stock);
        }
    }

    private boolean increaseStockIfExists(Stock stock) {
        return stocks.values().stream()
                .filter(existingStock -> existingStock.getProduct().name().equals(stock.getProduct().name()))
                .findFirst()
                .map(existingStock -> {
                    existingStock.updateQuantity(existingStock.getQuantity() + stock.getQuantity());
                    return true;
                })
                .orElse(false);
    }

    public void decreaseStock(String name, int quantity) {
        if(quantity ==0) return;
        promotionStocks.values().stream()
                .filter(existingStock -> existingStock.getProduct().name().equals(name))
                .findFirst()
                .map(existingStock -> {
                    Promotion promotion = existingStock.getPromotion();
                    if(!promotion.isValidStatus(existingStock.getQuantity())) {
                        decreaseStockOnly(name, quantity - existingStock.getQuantity());
                        decreasePromotionStock(name, existingStock.getQuantity());
                        return null;
                    }
                    decreaseStockOnly(name, quantity);
                    return null;
                });

    }
    private void decreaseStockOnly(String name, int quantity){
        stocks.values().stream()
                .filter(existingStock -> existingStock.getProduct().name().equals(name))
                .findFirst()
                .map(existingStock -> {
                    existingStock.updateQuantity(existingStock.getQuantity() - quantity);
                    return null;
                });
    }

    public void decreasePromotionStock(String name, int quantity) {
        if(quantity ==0)return;
        promotionStocks.values().stream()
                .filter(existingStock -> existingStock.getProduct().name().equals(name))
                .findFirst()
                .map(existingStock -> {
                    existingStock.updateQuantity(existingStock.getQuantity() - quantity);
                    return true;
                })
                .orElse(false);
    }

    public void addPromotionStock(PromotionStock promotionStock) {
        Integer sequenceId = getCurrentSequenceId();
        this.promotionStocks.put(sequenceId, promotionStock);
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        int currentSize = stocks.size() + promotionStocks.size();

        for (int i = 1; i <= currentSize; i++) {
            if (promotionStocks.containsKey(i)) {
                result.append(promotionStocks.get(i));
            }
            if (stocks.containsKey(i)) {
                result.append(stocks.get(i));
            }
            result.append(System.lineSeparator());
        }

        return result.toString().trim();
    }

    public Stream<PromotionStock> streamPromotionStock() {
        return promotionStocks.values().stream();
    }

    public Stream<Stock> streamStock() {
        return stocks.values().stream();
    }

    public boolean isExistProduct(String productName) {
        return streamStock()
                .anyMatch(stock -> stock.getProduct().name().equals(productName));
    }
    public boolean isAvailableQuantity(String name, int quantity) {
        int totalQuantity = Stream.concat(streamStock(), streamPromotionStock())
                .filter(stock -> stock.getProduct().name().equals(name))
                .mapToInt(stock -> stock.getQuantity())
                .sum();

        return totalQuantity >= quantity;
    }

    public int getPromotionStockCount(String name) {
        return streamPromotionStock()
                .filter(stock -> stock.getProduct().name().equals(name))
                .mapToInt(Stock::getQuantity)  // Assuming stock has a quantity field
                .findFirst()
                .orElse(0);  // Return 0 if the product is not found
    }

    public int getApplicableQuantity(CartItem cartItem, PromotionPolicy policy) {
        if (policy == null) return 0;
        int totalQuantity = getPromotionStockCount(cartItem.name());
        return policy.getApplicableQuantity(totalQuantity);
    }
}
