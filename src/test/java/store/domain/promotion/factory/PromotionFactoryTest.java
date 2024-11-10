package store.domain.promotion.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.common.exception.ErrorMessages;
import store.common.exception.InvalidPromotionException;
import store.domain.promotion.model.Promotion;
import store.domain.promotion.model.PromotionPolicy;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PromotionFactoryTest {

    private PromotionFactory promotionFactory;
    private PromotionPolicy policy1;
    private PromotionPolicy policy2;

    @BeforeEach
    void setUp() {
        promotionFactory = new PromotionFactory();
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);
        int buyQuantity = 3;
        int getQuantity = 1;

        policy1 = new PromotionPolicy(buyQuantity, getQuantity, startDate, endDate);
        policy2 = new PromotionPolicy(buyQuantity, getQuantity, startDate, endDate);
    }

    @Test
    void 정책추가_유효한정책_정상추가() {
        promotionFactory.addPolicy("SUMMER_SALE", policy1);

        assertEquals(1, promotionFactory.size(), "Promotion policy should be added.");
    }

    @Test
    void 정책추가_이름이Null일때_예외처리() {
        InvalidPromotionException exception = assertThrows(InvalidPromotionException.class, () -> {
            promotionFactory.addPolicy(null, policy1);
        });

        assertEquals(ErrorMessages.INVALID_PROMOTION.getMessage(), exception.getMessage());
    }

    @Test
    void 정책추가_정책이Null일때_예외처리() {
        InvalidPromotionException exception = assertThrows(InvalidPromotionException.class, () -> {
            promotionFactory.addPolicy("SUMMER_SALE", null);
        });

        assertEquals(ErrorMessages.INVALID_PROMOTION.getMessage(), exception.getMessage());
    }

    @Test
    void 프로모션조회_유효한이름_정상조회() {
        promotionFactory.addPolicy("SUMMER_SALE", policy1);

        Promotion promotion = promotionFactory.get("SUMMER_SALE");

        assertNotNull(promotion, "Promotion should be retrieved successfully.");
        assertEquals("SUMMER_SALE", promotion.name(), "Promotion name should match.");
    }

    @Test
    void 프로모션조회_유효하지않은이름_예외처리() {
        InvalidPromotionException exception = assertThrows(InvalidPromotionException.class, () -> {
            promotionFactory.get("INVALID_PROMOTION");
        });

        assertEquals(ErrorMessages.INVALID_PROMOTION.getMessage(), exception.getMessage());
    }

    @Test
    void 정책추가후_정책개수확인() {
        promotionFactory.addPolicy("SUMMER_SALE", policy1);
        promotionFactory.addPolicy("WINTER_SALE", policy2);

        assertEquals(2, promotionFactory.size(), "Size should reflect the number of added policies.");
    }
}
