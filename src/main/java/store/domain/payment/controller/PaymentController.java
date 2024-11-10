package store.domain.payment.controller;

import store.common.controller.BaseController;
import store.common.view.InputView;
import store.common.view.OutputView;
import store.domain.inventory.service.InventoryService;
import store.domain.order.model.Order;
import store.domain.payment.factory.ReceiptFactory;
import store.domain.payment.model.Payment;
import store.domain.payment.model.Receipt;
import store.domain.payment.service.PaymentService;

public class PaymentController extends BaseController {
    private final PaymentService paymentService;
    private final InventoryService inventoryService;

    public PaymentController(InputView inputView, OutputView outputView, PaymentService paymentService, InventoryService inventoryService) {
        super(inputView, outputView);
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
    }

    public void doPayment(Order order) {
        boolean isMembership = inputView.confirmMembership();
        Payment payment = paymentService.processPayment(order, isMembership);
        inventoryService.decreaseStockOfItem(order);
        Receipt receipt = ReceiptFactory.createReceipt(payment);
        outputView.displayMessage(receipt.toString());
    }

    public boolean askForAdditionalPurchaseAfterPayment() {
        return inputView.getContinueShoppingConfirmation();
    }

}
