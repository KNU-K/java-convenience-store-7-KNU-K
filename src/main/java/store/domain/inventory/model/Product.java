package store.domain.inventory.model;

public record Product(String name, Price price) {

    private static final String PRICE_SUFFIX = "원";
    private static final String BLANK = " ";

    @Override
    public String toString() {
        return name + BLANK + price + PRICE_SUFFIX;
    }

}
