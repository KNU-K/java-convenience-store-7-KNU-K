package store;

import store.common.config.SupplierConfig;
import store.common.handler.ConvenienceStoreManager;

public class Application {
    public static void main(String[] args) {
        SupplierConfig config = new SupplierConfig();
        ConvenienceStoreManager convenienceStoreManager = config.createConvenienceStoreManager();
        convenienceStoreManager.handleOrderProcess();
    }
}
