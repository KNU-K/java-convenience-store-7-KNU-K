package store.domain.inventory.service;

import org.junit.jupiter.api.extension.ExtendWith;
import store.extension.DataExtension;
import store.extension.InventoryExtension;
import store.common.exception.ErrorMessages;
import store.common.initializer.InventoryInitializer;
import store.domain.inventory.model.*;
import store.domain.order.model.OrderItem;
import store.domain.promotion.model.PromotionPolicy;
import store.common.exception.InvalidInventoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(InventoryExtension.class)
@ExtendWith(DataExtension.class)
class InventoryServiceTest {

    private InventoryService inventoryService;
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = InventoryInitializer.getInstance().getInventory();
        inventoryService = new InventoryService(inventory);
    }

    @Test
    void 재고상태_출력() {

        String inventoryStatus = inventoryService.getInventoryStatus();
        assertThat(inventoryStatus).contains(
                "- 콜라 1,000원 10개 탄산2+1",
                "- 콜라 1,000원 10개",
                "- 사이다 1,000원 8개 탄산2+1",
                "- 사이다 1,000원 7개",
                "- 오렌지주스 1,800원 9개 MD추천상품",
                "- 오렌지주스 1,800원 재고 없음",
                "- 탄산수 1,200원 5개 탄산2+1",
                "- 탄산수 1,200원 재고 없음",
                "- 물 500원 10개",
                "- 비타민워터 1,500원 6개",
                "- 감자칩 1,500원 5개 반짝할인",
                "- 감자칩 1,500원 5개",
                "- 초코바 1,200원 5개 MD추천상품",
                "- 초코바 1,200원 5개",
                "- 에너지바 2,000원 5개",
                "- 정식도시락 6,400원 8개",
                "- 컵라면 1,700원 1개 MD추천상품",
                "- 컵라면 1,700원 10개"
        );
    }

    @Test
    void 재고가_부족할_경우_예외발생() {
        // When & Then
        InvalidInventoryException exception = assertThrows(InvalidInventoryException.class, () -> {
            inventoryService.checkSufficientQuantity("콜라", 30);});
        assertEquals(ErrorMessages.EXCEEDS_STOCK.getMessage(), exception.getMessage());
    }

    @Test
    void 재고가_충분한_경우() {
        // When & Then
        assertDoesNotThrow(() -> {
            inventoryService.checkSufficientQuantity("콜라", 5); // 5개는 충분함
        });
    }

    @Test
    void 상품_가격_조회() {
        // When
        Price price = inventoryService.getProductPrice("콜라");

        // Then
        assertEquals(Price.of(1000), price);
    }

    @Test
    void 상품_주문_가격_계산() {
        // Given
        OrderItem orderItem = new OrderItem(new CartItem("콜라",3),Price.of(1000),0,null);
        // When
        Price totalPrice = inventoryService.getTotalPriceOfEachItem(orderItem);

        // Then
        assertEquals(new Price(3000), totalPrice);
    }

    @Test
    void 프로모션_적용_수량_계산() {
        // Given
        CartItem cartItem = new CartItem("콜라", 20);
        PromotionPolicy promotionPolicy = new PromotionPolicy(2,1, LocalDate.of(2024,11,7), LocalDate.of(2024,12,25)); // 프로모션 정책: 5개까지 적용

        // When
        int applicableQuantity = inventoryService.getApplicableQuantity(cartItem, promotionPolicy);

        // Then
        assertEquals(9, applicableQuantity);
    }
}

