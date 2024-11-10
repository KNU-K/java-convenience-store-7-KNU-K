package store.domain.promotion.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PromotionTest {

    @Test
    void 프로모션이_유효한지_확인한다() {
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);
        PromotionPolicy promotionPolicy = new PromotionPolicy(3, 1, startDate, endDate);
        Promotion promotion = new Promotion("Winter Sale", promotionPolicy);

        assertTrue(promotion.isValid());
    }

    @Test
    void 프로모션이_유효하지_않은_날짜라면_유효하지_않다고_판단한다() {
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 5);
        PromotionPolicy promotionPolicy = new PromotionPolicy(3, 1, startDate, endDate);
        Promotion promotion = new Promotion("Winter Sale", promotionPolicy);

        assertFalse(promotion.isValid());
    }

    @Test
    void 프로모션이_사은품_대상인지_확인한다() {
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);
        PromotionPolicy promotionPolicy = new PromotionPolicy(3, 1, startDate, endDate);
        Promotion promotion = new Promotion("Winter Sale", promotionPolicy);

        assertTrue(promotion.isEligibleForExtraItem(7));
        assertFalse(promotion.isEligibleForExtraItem(8));
    }

    @Test
    void 프로모션_상태가_유효하지_않은_수량이라면_유효하지_않다고_판단한다() {
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);
        PromotionPolicy promotionPolicy = new PromotionPolicy(3, 1, startDate, endDate);
        Promotion promotion = new Promotion("Winter Sale", promotionPolicy);

        assertFalse(promotion.isValidStatus(2));
        assertTrue(promotion.isValidStatus(3));
    }

    @Test
    void 프로모션에_정책이_없는_경우_유효하지_않다고_판단한다() {
        Promotion promotion = new Promotion("No Policy Sale", null);

        assertFalse(promotion.isValid());
    }

    @Test
    void 프로모션_이름을_반환한다() {
        Promotion promotion = new Promotion("Summer Sale", null);

        assertEquals("Summer Sale", promotion.toString());
    }
}
