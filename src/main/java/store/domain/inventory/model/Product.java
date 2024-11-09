package store.domain.inventory.model;

import java.util.Objects;

public record Product(String name, Price price) {

    private static final String PRICE_SUFFIX = "원";

    @Override
    public String toString() {
        return name + " " + price + PRICE_SUFFIX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
