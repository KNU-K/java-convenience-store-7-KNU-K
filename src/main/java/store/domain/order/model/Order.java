package store.domain.order.model;

import store.domain.inventory.model.Price;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;


public final class Order {
    private final List<OrderItem> orderItems;
    private final Price totalPrice;

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


    public Price totalPrice() {
        return totalPrice;
    }

}
