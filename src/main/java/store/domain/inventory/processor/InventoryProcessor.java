package store.domain.inventory.processor;

import store.domain.inventory.model.*;
import store.domain.promotion.factory.PromotionFactory;
import store.domain.promotion.model.Promotion;

public class InventoryProcessor {

    private static final String NULL_PROMOTION_CODE = "null";
    private static final int PROMOTION_CODE_INDEX = 3;

    private final Inventory inventory;
    private final PromotionFactory promotionFactory;

    public InventoryProcessor(Inventory inventory, PromotionFactory promotionFactory) {
        this.inventory = inventory;
        this.promotionFactory = promotionFactory;
    }

    public void processStockItem(String[] stockDetails) {
        String productName = stockDetails[0];
        Price productPrice = Price.of(Integer.parseInt(stockDetails[1]));
        int stockQuantity = Integer.parseInt(stockDetails[2]);
        String promotionCode = getPromotionCode(stockDetails);

        Product product = new Product(productName, productPrice);
        Promotion promotion = getPromotion(promotionCode);

        if (promotion != null) {
            inventory.addPromotionStock(new PromotionStock(product, stockQuantity, promotion));
            inventory.addStock(new Stock(product, 0));
            return;
        }

        inventory.addStock(new Stock(product, stockQuantity));
    }

    private String getPromotionCode(String[] stockDetails) {
        if (stockDetails.length > PROMOTION_CODE_INDEX) {
            return stockDetails[PROMOTION_CODE_INDEX];
        }
        return null;
    }

    private Promotion getPromotion(String promotionCode) {
        if (promotionCode != null && !promotionCode.equals(NULL_PROMOTION_CODE)) {
            return promotionFactory.get(promotionCode);
        }
        return null;
    }
}
