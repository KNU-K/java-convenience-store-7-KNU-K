package store.domain.promotion.model;

import org.junit.jupiter.api.Test;
import store.common.exception.InvalidPromotionException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PromotionPolicyTest {

    @Test
    void 유효한_프로모션_정책을_생성한다() {
        // Arrange
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);
        int buyQuantity = 3;
        int getQuantity = 1;

        // Act
        PromotionPolicy promotionPolicy = new PromotionPolicy(buyQuantity, getQuantity, startDate, endDate);

        // Assert
        assertNotNull(promotionPolicy);
    }

    @Test
    void 시작_날짜가_끝_날짜보다_이른_경우_예외를_던진다() {
        // Arrange
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 10, 30);
        int buyQuantity = 3;
        int getQuantity = 1;

        // Act & Assert
        assertThrows(InvalidPromotionException.class, () -> new PromotionPolicy(buyQuantity, getQuantity, startDate, endDate));
    }

    @Test
    void 프로모션이_유효한_날짜인지_확인한다() {
        // Arrange
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);
        PromotionPolicy promotionPolicy = new PromotionPolicy(3, 1, startDate, endDate);

        // Act & Assert
        assertTrue(promotionPolicy.isValidDate()); // 오늘이 11월 1일 ~ 30일 사이라면 유효
    }

    @Test
    void 프로모션이_유효하지_않은_날짜인지_확인한다() {
        // Arrange
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 5);
        PromotionPolicy promotionPolicy = new PromotionPolicy(3, 1, startDate, endDate);

        // Act & Assert
        assertFalse(promotionPolicy.isValidDate()); // 오늘이 11월 6일이라면 유효하지 않음
    }

    @Test
    void 구매_수량과_사은품_수량_합을_구한다() {
        // Arrange
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);
        int buyQuantity = 3;
        int getQuantity = 1;
        PromotionPolicy promotionPolicy = new PromotionPolicy(buyQuantity, getQuantity, startDate, endDate);

        // Act & Assert
        assertEquals(4, promotionPolicy.getTotalQuantityRequiredForPromotion());  // 3 + 1 = 4
    }

    @Test
    void 구매_수량에_따라_사은품_수량을_계산한다() {
        // Arrange
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);
        PromotionPolicy promotionPolicy = new PromotionPolicy(3, 1, startDate, endDate);

        // Act & Assert
        assertEquals(2, promotionPolicy.getGiftQuantity(8)); // 8개 구매 시 증정품 2개
    }

    @Test
    void 구매_수량에_따라_사은품_대상인지_확인한다() {
        // Arrange
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);
        PromotionPolicy promotionPolicy = new PromotionPolicy(3, 1, startDate, endDate);

        // Act & Assert
        assertTrue(promotionPolicy.isEligibleForExtraItem(7));  // 7개 구매 시 프로모션에 적합
        assertFalse(promotionPolicy.isEligibleForExtraItem(8)); // 8개 구매 시 프로모션에 적합하지 않음
    }

    @Test
    void 유효하지_않은_수량에_대해_예외를_던진다() {
        // Arrange
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);
        PromotionPolicy promotionPolicy = new PromotionPolicy(3, 1, startDate, endDate);

        // Act & Assert
        assertThrows(InvalidPromotionException.class, () -> promotionPolicy.getGiftQuantity(-1)  // 음수 수량은 유효하지 않음
        );
    }

    @Test
    void 프로모션_수량이_적절한지_확인한다() {
        // Arrange
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);
        PromotionPolicy promotionPolicy = new PromotionPolicy(3, 1, startDate, endDate);

        // Act & Assert
        assertTrue(promotionPolicy.isValidPromotionQuantity(3));  // 최소 구매 수량이 3 이상이면 유효
        assertFalse(promotionPolicy.isValidPromotionQuantity(2)); // 2는 유효하지 않음
    }
}
