package store.domain.promotion.model;

public record Promotion(String name, PromotionPolicy policy) {

    @Override
    public String toString() {
        return name;
    }

    public boolean isValid() {
        return isExistPolicy() && policy.isValidDate();
    }

    public boolean isEligibleForExtraItem(int quantity) {
        return isExistPolicy() && policy.isEligibleForExtraItem(quantity);
    }

    private boolean isExistPolicy() {
        return policy != null;
    }
}
