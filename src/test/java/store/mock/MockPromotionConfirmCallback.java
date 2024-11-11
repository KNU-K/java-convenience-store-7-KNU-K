package store.mock;

import store.domain.inventory.model.CartItem;
import store.domain.order.service.PromotionConfirmCallback;

public class MockPromotionConfirmCallback implements PromotionConfirmCallback {
    @Override
    public boolean confirmExtraPromotion(CartItem cartItem) {
        return true;  // 프로모션이 항상 승인된다고 가정
    }

    @Override
    public boolean confirmRegularPriceOption(CartItem cartItem, int remainingQuantity) {
        return false;  // 기본 옵션을 항상 거부한다고 가정
    }
}

