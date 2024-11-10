package store.domain.inventory.model;

public record Product(String name, Price price) {

    private static final String PRICE_SUFFIX = "원";

    @Override
    public String toString() {
        return name + " " + price + PRICE_SUFFIX;
    }

}
