package store.domain.inventory.model;

public class CartItem {
    private final String name;
    private int quantity;


    public CartItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public void increaseQuantity() {
        quantity += 1;
    }

    public String name() {
        return name;
    }

    public int quantity() {
        return quantity;
    }

    public void decreaseQuantity(int quantity) {
        this.quantity -= quantity;
    }


    public void setQuantity(int finalQuantity) {
        this.quantity = finalQuantity;
    }
}
