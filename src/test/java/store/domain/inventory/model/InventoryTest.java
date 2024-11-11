package store.domain.inventory.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.promotion.model.Promotion;
import store.domain.promotion.model.PromotionPolicy;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private static final LocalDate FIXED_DATE = LocalDate.of(2024, 11, 11);
    private Inventory inventory;
    private Product product;
    private Stock stock;
    private Promotion promotion;
    private PromotionStock promotionStock;
    private PromotionPolicy promotionPolicy;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        product = new Product("상품1", new Price(1000));
        stock = new Stock(product, 10);
        promotionPolicy = new PromotionPolicy(2, 1, FIXED_DATE.minusDays(1), FIXED_DATE.plusDays(1));
        promotion = new Promotion("프로모션1", promotionPolicy);
        promotionStock = new PromotionStock(product, 5, promotion);
    }

    @Test
    void 재고_추가() {
        inventory.addStock(stock);
        assertTrue(inventory.isExistProduct("상품1"));
    }

    @Test
    void 재고_존재_시_수량_증가() {
        inventory.addStock(stock);
        inventory.addStock(new Stock(product, 5));

        assertEquals(15, inventory.streamStock().findFirst().get().getQuantity());
    }

    @Test
    void 프로모션_재고_차감() {
        inventory.addPromotionStock(promotionStock);
        inventory.decreasePromotionStock("상품1", 2);

        assertEquals(3, inventory.streamPromotionStock().findFirst().get().getQuantity());
    }

    @Test
    void 이용가능한_수량_확인() {
        inventory.addStock(stock);
        inventory.addPromotionStock(promotionStock);

        assertTrue(inventory.isAvailableQuantity("상품1", 12));
        assertFalse(inventory.isAvailableQuantity("상품1", 20));
    }

    @Test
    void 적용가능한_수량() {
        inventory.addPromotionStock(promotionStock);
        CartItem cartItem = new CartItem("상품1", 3);
        assertEquals(3, inventory.getApplicableQuantity(cartItem, promotionPolicy));
    }

    @Test
    void 프로모션_유효성() {
        assertTrue(promotion.isValid());
    }

    @Test
    void 프로모션_조건_적합성() {
        assertTrue(promotion.isEligibleForExtraItem(2));
    }
}
