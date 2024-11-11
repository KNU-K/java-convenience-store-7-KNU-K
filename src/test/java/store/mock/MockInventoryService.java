package store.mock;

import store.common.initializer.InventoryInitializer;
import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Price;
import store.domain.inventory.service.InventoryService;
import store.domain.order.model.OrderItem;
import store.domain.promotion.model.PromotionPolicy;

public class MockInventoryService extends InventoryService {
    private static final int COKE_PRICE = 1000;
    private String expectedInventoryStatus;

    public MockInventoryService() {
        super(InventoryInitializer.getInstance().getInventory());
    }

    @Override
    public Price getProductPrice(String name) {
        return new Price(100);  // 각 상품의 가격을 100으로 설정
    }

    @Override
    public void checkSufficientQuantity(String name, int quantity) {
        // 충분한 수량이 있다고 가정하고 아무 작업도 하지 않음
    }

    @Override
    public int getApplicableQuantity(CartItem cartItem, PromotionPolicy policy) {
        return cartItem.quantity();
    }

    @Override
    public Price getTotalPriceOfEachItem(OrderItem orderItem) {
        return Price.of(orderItem.quantity() * COKE_PRICE);  // 수량에 따른 총 가격 계산
    }

    public void setInventoryStatus(String expectedInventoryStatus) {
        this.expectedInventoryStatus = expectedInventoryStatus;
    }

    @Override
    public String toString() {
        return expectedInventoryStatus;
    }
}

