package store.domain.order.model;

import store.domain.inventory.model.Price;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;


public final class Order {
    private final List<OrderItem> orderItems;
    private Price totalPrice;

    public Order(List<OrderItem> orderItems, Price totalPrice) {
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
    }

    public Stream<OrderItem> streamOrderItems() {
        return orderItems.stream();
    }

    public void forEachItems(Consumer<OrderItem> action) {
        orderItems.forEach(action);
    }

    public List<OrderItem> orderItems() {
        return orderItems;
    }

    public Price totalPrice() {
        return totalPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Order) obj;
        return Objects.equals(this.orderItems, that.orderItems) &&
                Objects.equals(this.totalPrice, that.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItems, totalPrice);
    }

    @Override
    public String toString() {
        return "Order[" +
                "orderItems=" + orderItems + ", " +
                "totalPrice=" + totalPrice + ']';
    }


}
