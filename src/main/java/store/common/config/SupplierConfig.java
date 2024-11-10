package store.common.config;

import store.common.handler.ConvenienceStoreManager;
import store.common.handler.InputRetryHandler;
import store.common.initializer.DataInitializer;
import store.common.initializer.StoreInitializer;
import store.common.view.InputView;
import store.common.view.OutputView;
import store.domain.inventory.controller.InventoryController;
import store.domain.order.controller.OrderController;
import store.domain.payment.controller.PaymentController;

import java.util.function.Supplier;

public class SupplierConfig {
    private static final int MAXIMUM_RETRIES = 5;

    private final InputRetryHandler inputRetryHandler = new InputRetryHandler(MAXIMUM_RETRIES);
    private final ConvenienceStoreManager convenienceStoreManager;

    public SupplierConfig() {
        DataInitializer dataInitializer = new DataInitializer();
        StoreInitializer storeInitializer = new StoreInitializer();

        Supplier<InventoryController> shoppingControllerSupplier = () -> new InventoryController(createInputView(), createOutputView(), storeInitializer.getShoppingService());
        Supplier<OrderController> orderControllerSupplier = () -> new OrderController(createInputView(), createOutputView(), storeInitializer.getOrderService());
        Supplier<PaymentController> paymentControllerSupplier = () -> new PaymentController(createInputView(), createOutputView(), storeInitializer.getPaymentService(), storeInitializer.getShoppingService());

        this.convenienceStoreManager = new ConvenienceStoreManager(shoppingControllerSupplier, orderControllerSupplier, paymentControllerSupplier);

        dataInitializer.initializeData();
    }

    private static OutputView createOutputView() {
        return new OutputView();
    }

    public ConvenienceStoreManager createConvenienceStoreManager() {
        return convenienceStoreManager;
    }

    private InputView createInputView() {
        return new InputView(inputRetryHandler);
    }

}
