package store.mock;

import store.domain.inventory.model.CartItem;
import store.domain.promotion.model.PromotionPolicy;
import store.domain.promotion.service.PromotionService;

import java.time.LocalDate;

public class MockPromotionService extends PromotionService {
    @Override
    public PromotionPolicy getPromotionPolicy(CartItem cartItem) {
        return new PromotionPolicy(1, 2, LocalDate.of(2024, 11, 7), LocalDate.of(2024, 12, 25));
    }
}

