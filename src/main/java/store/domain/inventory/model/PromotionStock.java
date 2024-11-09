package store.domain.inventory.model;

import store.domain.promotion.model.Promotion;

public class PromotionStock extends Stock {
    private final Promotion promotion;

    public PromotionStock(Product product, Integer quantity, Promotion promotion) {
        super(product, quantity);
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return super.toString() + " " + promotion;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public boolean isValidPromotionDate() {
        return promotion.isValid();
    }
}
