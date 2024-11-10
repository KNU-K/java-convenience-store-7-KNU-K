package store.domain.promotion.strategy;

public enum PromotionStrategyType {
    EXTRA_QUANTITY("추가 상품"),
    REGULAR_PRICE_STRATEGY("정가 구매");

    private final String description;

    PromotionStrategyType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}