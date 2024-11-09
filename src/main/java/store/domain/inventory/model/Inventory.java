package store.domain.inventory.model;


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
        stocks.values().stream()
                .filter(existingStock -> existingStock.getProduct().name().equals(name))
                .findFirst()
                .map(existingStock -> {
                    existingStock.updateQuantity(existingStock.getQuantity() - quantity);
                    return true;
                })
                .orElse(false);
    }

    public void decreasePromotionStock(String name, int quantity) {
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
}
