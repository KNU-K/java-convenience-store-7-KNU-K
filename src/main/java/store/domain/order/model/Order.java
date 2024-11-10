package store.domain.order.model;

import store.domain.inventory.model.Price;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public record Order(List<OrderItem> orderItems, Price totalPrice) {

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
