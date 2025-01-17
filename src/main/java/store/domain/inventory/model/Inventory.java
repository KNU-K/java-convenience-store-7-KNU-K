package store.domain.inventory.model;

import store.domain.promotion.model.PromotionPolicy;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Inventory {

    private static final int INIT_SEQUENCE_ID = 1;
    private static final int NOTHING = 0;

    private final Map<Integer, Stock> stocks = new HashMap<>();
    private final Map<Integer, PromotionStock> promotionStocks = new HashMap<>();

    public boolean isExistProduct(String productName) {
        return streamStock().anyMatch(stock -> stock.getProduct().name().equals(productName));
    }

    public boolean isAvailableQuantity(String name, int quantity) {
        int totalQuantity = Stream.concat(streamStock(), streamPromotionStock())
                .filter(stock -> stock.getProduct().name().equals(name))
                .mapToInt(Stock::getQuantity)
                .sum();
        return totalQuantity >= quantity;
    }

    public int getPromotionStockCount(String name) {
        return streamPromotionStock()
                .filter(stock -> stock.getProduct().name().equals(name))
                .mapToInt(Stock::getQuantity)
                .findFirst()
                .orElse(NOTHING);
    }

    public int getApplicableQuantity(CartItem cartItem, PromotionPolicy policy) {
        if (policy == null) {
            return NOTHING;
        }
        int totalQuantity = getPromotionStockCount(cartItem.name());
        if (totalQuantity < cartItem.quantity()) {
            return policy.getApplicableQuantity(totalQuantity);
        }
        return policy.getApplicableQuantity(cartItem.quantity());
    }

    public void addStock(Stock stock) {
        if (increaseStockIfExists(stock)) return;
        Integer sequenceId = getCurrentSequenceId();
        stocks.put(sequenceId, stock);
    }

    public void addPromotionStock(PromotionStock promotionStock) {
        Integer sequenceId = getCurrentSequenceId();
        promotionStocks.put(sequenceId, promotionStock);
    }

    public void decreaseStock(String name, int quantity) {
        if (quantity == NOTHING) {
            return;
        }

        Optional<PromotionStock> promotionStockOpt = promotionStocks.values().stream()
                .filter(existingStock -> existingStock.getProduct().name().equals(name))
                .findFirst();

        if (promotionStockOpt.isPresent()) {
            PromotionStock existingStock = promotionStockOpt.get();

            decreaseStockOnly(name, quantity - existingStock.getQuantity());
            decreasePromotionStock(name, existingStock.getQuantity());
            return;
        }

        decreaseStockOnly(name, quantity);
    }

    private void decreaseStockOnly(String name, int quantity) {
        if (quantity == NOTHING) {
            return;
        }
        stocks.values().stream()
                .filter(existingStock -> existingStock.getProduct().name().equals(name))
                .findFirst()
                .ifPresent(existingStock -> existingStock.updateQuantity(existingStock.getQuantity() - quantity));
    }

    public void decreasePromotionStock(String name, int quantity) {
        if (quantity == NOTHING) {
            return;
        }
        promotionStocks.values().stream()
                .filter(existingStock -> existingStock.getProduct().name().equals(name))
                .findFirst()
                .ifPresent(existingStock -> existingStock.updateQuantity(existingStock.getQuantity() - quantity));
    }

    public Stream<PromotionStock> streamPromotionStock() {
        return promotionStocks.values().stream();
    }

    public Stream<Stock> streamStock() {
        return stocks.values().stream();
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

    private Integer getCurrentSequenceId() {
        return INIT_SEQUENCE_ID + stocks.size() + promotionStocks.size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int currentSize = stocks.size() + promotionStocks.size();

        for (int currentSequenceNumber = INIT_SEQUENCE_ID; currentSequenceNumber <= currentSize; currentSequenceNumber++) {
            if (promotionStocks.containsKey(currentSequenceNumber)) {
                result.append(promotionStocks.get(currentSequenceNumber));
            }
            if (stocks.containsKey(currentSequenceNumber)) {
                result.append(stocks.get(currentSequenceNumber));
            }
            result.append(System.lineSeparator());
        }

        return result.toString().trim();
    }
}
