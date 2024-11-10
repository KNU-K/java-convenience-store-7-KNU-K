package store.domain.inventory.model;

import java.text.DecimalFormat;

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

    @Override
    public String toString() {
        return formatAmount(amount);
    }

    public Price multiply(int quantity) {
        return new Price(amount * quantity);
    }

    public Price applyPercentage(int percentage) {
        Price a = Price.of((int) (amount * (percentage / 100.0)));
        return a;
    }

    public Price plus(Price other) {
        return Price.of(this.amount + other.amount);
    }

    public Price subtract(Price other) {
        return Price.of(this.amount - other.amount);
    }

    private String formatAmount(int amount) {
        return new DecimalFormat(NUMBER_FORMAT).format(amount);
    }

}
