package store.domain.inventory.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class Cart {
    private final List<CartItem> cartItems;

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    public void addItem(CartItem item) {
        cartItems.add(item);
    }

    public Stream<CartItem> streamItem() {
        return cartItems.stream();
    }
}
