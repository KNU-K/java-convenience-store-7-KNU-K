package store.common.initializer;

import store.domain.inventory.model.Inventory;

public class InventoryInitializer {
    private static InventoryInitializer instance;
    private Inventory inventory;

    private InventoryInitializer() {
        inventory = new Inventory();
    }

    public static InventoryInitializer getInstance() {
        if (instance == null) {
            instance = new InventoryInitializer();
        }
        return instance;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void resetInventory() {
        this.inventory = new Inventory();
    }
}
