package store.domain.inventory.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import store.extension.DataExtension;
import store.common.initializer.InventoryInitializer;
import store.domain.inventory.model.Cart;
import store.mock.MockInputView;
import store.mock.MockInventoryService;
import store.mock.MockOutputView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@ExtendWith(DataExtension.class)
class InventoryControllerTest {
    private InventoryController inventoryController;
    private MockInputView inputView;
    private MockOutputView outputView;
    private MockInventoryService inventoryService;

    @BeforeEach
    void setup() {
        inputView = new MockInputView(null);
        outputView = new MockOutputView();
        inventoryService = new MockInventoryService();
        inventoryController = new InventoryController(inputView, outputView, inventoryService);
    }

    @Test
    void 상품을_장바구니에_추가() {
        // Given
        String expectedProductName = "콜라";
        int expectedQuantity = 5;
        inputView.setProductNameAndQuantity("[콜라-5]");
        // When
        Cart cart = inventoryController.addToCart();

        //Then
        assertEquals(expectedProductName, cart.streamItem().findFirst().get().name());
        assertEquals(expectedQuantity, cart.streamItem().findFirst().get().quantity());
    }

    @Test
    void 재고_상태_출력() {
        // Given
        String expectedInventoryStatus = InventoryInitializer.getInstance().getInventory().toString();

        inputView.setProductNameAndQuantity("[콜라-5]");
        inventoryService.setInventoryStatus(expectedInventoryStatus);

        // When
        inventoryController.addToCart();

        // Then
        assertTrue(outputView.isInventoryStatusDisplayed());
        assertEquals(expectedInventoryStatus, outputView.getDisplayedInventoryStatus());
    }
}
