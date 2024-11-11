package store.domain.promotion.factory;

import store.common.constants.ErrorMessages;
import store.common.exception.InvalidPromotionException;
import store.domain.promotion.model.Promotion;
import store.domain.promotion.model.PromotionPolicy;

import java.util.HashMap;
import java.util.Map;

public class PromotionFactory {
    private final Map<String, PromotionPolicy> policies;

    public PromotionFactory() {
        this.policies = new HashMap<>();
    }

    public void addPolicy(String name, PromotionPolicy policy) {
        if (name == null || policy == null) {
            throw new InvalidPromotionException(ErrorMessages.INVALID_PROMOTION);
        }
        policies.put(name, policy);
    }

    public Promotion get(String name) {
        PromotionPolicy policy = policies.get(name);
        if (policy == null) {
            throw new InvalidPromotionException(ErrorMessages.INVALID_PROMOTION);
        }
        return new Promotion(name, policy);
    }

    public int size() {
        return policies.size();
    }

}