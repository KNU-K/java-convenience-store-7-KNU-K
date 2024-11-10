package store.domain.inventory.controller;

import store.common.controller.BaseController;
import store.common.view.InputView;
import store.common.view.OutputView;
import store.domain.inventory.model.Cart;
import store.domain.inventory.service.InventoryService;

public class InventoryController extends BaseController {
    private final InventoryService inventoryService;

    public InventoryController(InputView inputView, OutputView outputView, InventoryService inventoryService) {
        super(inputView, outputView);
        this.inventoryService = inventoryService;
    }

    public Cart addToCart() {
        displayInventoryStatus();
        return inputView.getProductNameAndQuantity();

    }

    private void displayInventoryStatus() {
        String inventoryStatus = inventoryService.getInventoryStatus();
        outputView.displayInventoryStatus(inventoryStatus);
    }
}
