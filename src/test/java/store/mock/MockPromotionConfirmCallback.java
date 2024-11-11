package store.mock;

import store.domain.inventory.model.CartItem;
import store.domain.order.service.PromotionConfirmCallback;

public class MockPromotionConfirmCallback implements PromotionConfirmCallback {
    @Override
    public boolean confirmExtraPromotion(CartItem cartItem) {
        return true;
    }

    @Override
    public boolean confirmRegularPriceOption(CartItem cartItem, int remainingQuantity) {
        return false;
    }
}

