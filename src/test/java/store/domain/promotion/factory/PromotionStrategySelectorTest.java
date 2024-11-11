package store.domain.promotion.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import store.extension.InventoryExtension;
import store.domain.inventory.model.CartItem;
import store.domain.promotion.model.PromotionPolicy;
import store.domain.promotion.strategy.PromotionStrategy;
import store.domain.promotion.strategy.PromotionStrategyType;
import store.domain.promotion.strategy.RegularPriceStrategy;
import store.domain.promotion.strategy.ExtraQuantityStrategy;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(InventoryExtension.class)
public class PromotionStrategySelectorTest {

    private CartItem cartItem;
    private CartItem cartItem2;
    private PromotionPolicy validExtraPolicy;
    private PromotionPolicy invalidPolicy;

    @BeforeEach
    void setUp() {
        validExtraPolicy = new PromotionPolicy(2, 1, LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 30));
        invalidPolicy = new PromotionPolicy(3, 1, LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 30));

        cartItem = new CartItem("item1", 2);
        cartItem2 = new CartItem("item2", 10);

    }

    @Test
    void 선택_유효한_프로모션_전략_확인() {

        PromotionStrategy promotionStrategy = PromotionStrategySelector.select(cartItem, validExtraPolicy);
        assertNotNull(promotionStrategy);
        assertTrue(promotionStrategy instanceof ExtraQuantityStrategy);
    }

    @Test
    void 선택_정상_가격_프로모션_전략_확인() {
        PromotionStrategy promotionStrategy = PromotionStrategySelector.select(cartItem2, validExtraPolicy);
        assertNotNull(promotionStrategy);
        assertTrue(promotionStrategy instanceof RegularPriceStrategy);
    }

    @Test
    void 선택_프로모션_정책_없을_때_확인() {
        PromotionStrategy promotionStrategy = PromotionStrategySelector.select(cartItem, null);
        assertNull(promotionStrategy);
    }

    @Test
    void 선택_프로모션_기간_유효하지_않을_때_확인() {
        PromotionPolicy expiredPolicy = new PromotionPolicy(3, 1, LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 30));
        PromotionStrategy promotionStrategy = PromotionStrategySelector.select(cartItem, expiredPolicy);
        assertNull(promotionStrategy);
    }

    @Test
    void 선택_유효하지_않은_정책_확인() {
        PromotionStrategy promotionStrategy = PromotionStrategySelector.select(cartItem, invalidPolicy);
        assertNull(promotionStrategy);
    }

    @Test
    void 전략_유형_변환_확인() {
        PromotionStrategy extraQuantityStrategy = new ExtraQuantityStrategy();
        PromotionStrategy regularPriceStrategy = new RegularPriceStrategy();

        assertEquals(PromotionStrategyType.EXTRA_QUANTITY, PromotionStrategySelector.toType(extraQuantityStrategy));
        assertEquals(PromotionStrategyType.REGULAR_PRICE_STRATEGY, PromotionStrategySelector.toType(regularPriceStrategy));
    }

    @Test
    void 전략_가져오기_확인() {
        PromotionStrategy extraQuantityStrategy = PromotionStrategySelector.get(PromotionStrategyType.EXTRA_QUANTITY);
        PromotionStrategy regularPriceStrategy = PromotionStrategySelector.get(PromotionStrategyType.REGULAR_PRICE_STRATEGY);

        assertNotNull(extraQuantityStrategy);
        assertNotNull(regularPriceStrategy);
    }


}
