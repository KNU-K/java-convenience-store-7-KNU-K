package store.domain.inventory.processor;

import store.domain.inventory.model.*;
import store.domain.promotion.factory.PromotionFactory;
import store.domain.promotion.model.Promotion;

public class InventoryProcessor {

    private final Inventory inventory;
    private final PromotionFactory promotionFactory; // PromotionFactory를 필드로 추가

    public InventoryProcessor(Inventory inventory, PromotionFactory promotionFactory) {
        this.inventory = inventory;
        this.promotionFactory = promotionFactory; // 생성자에서 초기화
    }

    public void processStockItem(String[] parts) {
        String name = parts[0];
        Price price = Price.of(Integer.parseInt(parts[1]));
        int quantity = Integer.parseInt(parts[2]);
        String promotionText = parts.length > 3 ? parts[3] : null;

        Product product = new Product(name, price);
        Promotion promotion = getPromotion(promotionText);

        if (promotion != null) {
            inventory.addPromotionStock(new PromotionStock(product, quantity, promotion));
            inventory.addStock(new Stock(product, 0));
        } else {
            inventory.addStock(new Stock(product, quantity));
        }
    }

    private Promotion getPromotion(String promotionText) {
        if (promotionText != null && !promotionText.equals("null")) {
            return promotionFactory.get(promotionText);
        }
        return null;
    }
}
