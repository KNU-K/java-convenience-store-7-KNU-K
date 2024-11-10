package store.domain.order.model;

import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Price;
import store.domain.promotion.model.PromotionPolicy;

import java.util.Objects;

public final class OrderItem {
    private final String name;
    private final Price price;
    private final PromotionPolicy promotionPolicy;
    private int quantity;
    private int promotionQuantity;

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

    public void increaseQuantity() {
        this.quantity += 1;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (OrderItem) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.price, that.price) &&
                this.quantity == that.quantity &&
                this.promotionQuantity == that.promotionQuantity &&
                Objects.equals(this.promotionPolicy, that.promotionPolicy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, quantity, promotionQuantity, promotionPolicy);
    }

    @Override
    public String toString() {
        return "OrderItem[" +
                "name=" + name + ", " +
                "price=" + price + ", " +
                "quantity=" + quantity + ", " +
                "promotionQuantity=" + promotionQuantity + ", " +
                "promotionPolicy=" + promotionPolicy + ']';
    }

    public void decreaseQuantity(int remainingQuantity) {
        quantity -= remainingQuantity;
    }


    public void updatePromotionQuantity(int applicableQuantity) {
        promotionQuantity = applicableQuantity;
    }
}
