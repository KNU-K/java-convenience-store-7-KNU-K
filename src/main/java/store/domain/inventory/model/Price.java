package store.domain.inventory.model;

import java.text.DecimalFormat;
import java.util.Objects;

public class Price {

    public static final Price ZERO = Price.of(0);
    private static final String NUMBER_FORMAT = "#,###";

    private final int amount;

    public Price(int amount) {
        this.amount = amount;
    }

    public static Price of(int amount) {
        return new Price(amount);
    }

    public Price multiply(int quantity) {
        return new Price(amount * quantity);
    }

    public Price applyPercentage(int percentage) {
        return Price.of((int) (amount * (percentage / 100.0)));
    }

    public Price plus(Price other) {
        return Price.of(this.amount + other.amount);
    }

    public Price subtract(Price other) {
        return Price.of(this.amount - other.amount);
    }

    public boolean isBiggerThan(Price other) {
        return amount > other.amount;
    }

    @Override
    public String toString() {
        return formatAmount(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return amount == price.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    private String formatAmount(int amount) {
        return new DecimalFormat(NUMBER_FORMAT).format(amount);
    }
}
