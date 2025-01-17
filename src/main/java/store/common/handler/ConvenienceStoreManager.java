package store.common.handler;

import store.common.view.OutputView;
import store.domain.inventory.controller.InventoryController;
import store.domain.inventory.model.Cart;
import store.domain.order.controller.OrderController;
import store.domain.order.model.Order;
import store.domain.payment.controller.PaymentController;

import java.util.function.Supplier;

public class ConvenienceStoreManager {
    private final Supplier<InventoryController> shoppingControllerSupplier;
    private final Supplier<OrderController> orderControllerSupplier;
    private final Supplier<PaymentController> paymentControllerSupplier;
    private final OutputView outputView;

    public ConvenienceStoreManager(Supplier<InventoryController> shoppingControllerSupplier,
                                   Supplier<OrderController> orderControllerSupplier,
                                   Supplier<PaymentController> paymentControllerSupplier,
                                   OutputView outputView) {
        this.shoppingControllerSupplier = shoppingControllerSupplier;
        this.orderControllerSupplier = orderControllerSupplier;
        this.paymentControllerSupplier = paymentControllerSupplier;
        this.outputView = outputView;
    }

    public void handleOrderProcess() {
        InventoryController inventoryController = shoppingControllerSupplier.get();
        OrderController orderController = orderControllerSupplier.get();
        PaymentController paymentController = paymentControllerSupplier.get();
        while (true) {
            try {
                Cart cart = inventoryController.addToCart();
                Order order = orderController.placeOrder(cart);
                paymentController.doPayment(order);

            } catch (RuntimeException error) {
                outputView.displayMessage(error.getMessage());
            } finally {
                if (!paymentController.askForAdditionalPurchaseAfterPayment()) {
                    return;
                }
            }
        }
    }
}
