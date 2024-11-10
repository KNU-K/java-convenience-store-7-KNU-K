package store.domain.order.model;

import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Price;
import store.domain.promotion.model.PromotionPolicy;

public record OrderItem(String name, Price price, int quantity, int promotionQuantity,
                        PromotionPolicy promotionPolicy) {

    public OrderItem(CartItem item, Price price, int promotionQuantity, PromotionPolicy promotionPolicy) {
        this(item.name(), price, item.quantity(), promotionQuantity, promotionPolicy);
    }
}
