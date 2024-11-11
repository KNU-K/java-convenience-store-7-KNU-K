package store.domain.inventory.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.domain.promotion.model.Promotion;
import store.domain.promotion.model.PromotionPolicy;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PromotionStockTest {

    private Product product;
    private Promotion promotion;
    private PromotionStock promotionStock;

    static Stream<Arguments> provideQuantityForValidPromotion() {
        return Stream.of(
                Arguments.of(5, true),
                Arguments.of(15, true),
                Arguments.of(2, true)
        );
    }

    @BeforeEach
    void setUp() {
        product = new Product("Product A", new Price(10000));
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);
        PromotionPolicy policy = new PromotionPolicy(2, 1, startDate, endDate);
        promotion = new Promotion("Winter Sale", policy);
        promotionStock = new PromotionStock(product, 10, promotion);
    }

    @Test
    void 프로모션_유효성_확인() {
        assertTrue(promotionStock.isValidPromotionDate());
    }

    @Test
    void 프로모션_정책_없을_경우_유효성_확인() {
        Promotion invalidPromotion = new Promotion("Invalid Promotion", null);
        PromotionStock invalidStock = new PromotionStock(product, 10, invalidPromotion);
        assertFalse(invalidStock.isValidPromotionDate());
    }

    @Test
    void 프로모션_정보_출력() {
        String expectedString = "- " + product.toString() + " 10개 Winter Sale";
        assertEquals(expectedString, promotionStock.toString());
    }

    @ParameterizedTest
    @MethodSource("provideQuantityForValidPromotion")
    void 주어진_수량에_대해_프로모션이_유효한지_확인한다(int quantity, boolean expectedValidity) {
        PromotionStock stock = new PromotionStock(product, quantity, promotion);
        assertEquals(expectedValidity, stock.isValidPromotionDate());
    }

    @Test
    void 프로모션_세부사항_반환() {
        assertEquals(promotion, promotionStock.getPromotion());
    }
}
