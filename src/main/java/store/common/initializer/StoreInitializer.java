package store.common.initializer;

import store.domain.inventory.model.Inventory;
import store.domain.inventory.service.InventoryService;
import store.domain.order.service.OrderService;
import store.domain.payment.service.PaymentService;
import store.domain.promotion.service.PromotionService;


public class StoreInitializer {
    private final Inventory inventory = InventoryInitializer.getInstance().getInventory();

    private final InventoryService inventoryService = new InventoryService(inventory);

    private final PromotionService promotionService = new PromotionService(inventory);
    private final OrderService orderService = new OrderService(inventoryService, promotionService);
    private final PaymentService paymentService = new PaymentService();

    public InventoryService getShoppingService() {
        return inventoryService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public OrderService getOrderService() {
        return orderService;
    }
}
