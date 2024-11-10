package store.domain.inventory.model;

import java.util.Objects;

public class Stock {

    private static final String BULLET = "- ";
    private static final String OUT_OF_STOCK_MESSAGE = "재고 없음";
    private static final String QUANTITY_UNIT = "개";

    private final Product product;
    private Integer quantity;

    public Stock(Product product, Integer quantity) {
        this.product = product;
        if (quantity == null) {
            quantity = 0;
        }
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isProductNameEqual(String productName) {
        return Objects.equals(productName, this.product.name());
    }

    @Override
    public String toString() {
        return BULLET + product + " " + getQuantityDisplay();
    }

    private String getQuantityDisplay() {
        if (quantity == 0) {
            return OUT_OF_STOCK_MESSAGE;
        }
        return quantity + QUANTITY_UNIT;
    }

}
