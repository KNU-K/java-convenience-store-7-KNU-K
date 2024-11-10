package store;

import store.common.config.SupplierConfig;
import store.common.handler.ConvenienceStoreManager;
import store.common.initializer.InventoryInitializer;

public class Application {
    public static void main(String[] args) {
        SupplierConfig config = new SupplierConfig();
        ConvenienceStoreManager convenienceStoreManager = config.createConvenienceStoreManager();
        convenienceStoreManager.handleOrderProcess();

        InventoryInitializer.getInstance().resetInventory();
    }
}
