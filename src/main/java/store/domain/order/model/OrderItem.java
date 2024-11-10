package store.domain.order.model;

import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Price;
import store.domain.promotion.model.PromotionPolicy;

public final class OrderItem {
    private final String name;
    private final Price price;
    private final PromotionPolicy promotionPolicy;
    private final int quantity;
    private final int promotionQuantity;

    public OrderItem(String name, Price price, int quantity, int promotionQuantity,
                     PromotionPolicy promotionPolicy) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotionQuantity = promotionQuantity;
        this.promotionPolicy = promotionPolicy;
    }

    public OrderItem(CartItem item, Price price, int promotionQuantity, PromotionPolicy promotionPolicy) {
        this(item.name(), price, item.quantity(), promotionQuantity, promotionPolicy);
    }

    public String name() {
        return name;
    }

    public Price price() {
        return price;
    }

    public int quantity() {
        return quantity;
    }

    public int promotionQuantity() {
        return promotionQuantity;
    }

    public PromotionPolicy promotionPolicy() {
        return promotionPolicy;
    }
}
