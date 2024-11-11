package store.mock;

import store.common.view.OutputView;

public class MockOutputView extends OutputView {
    private boolean messageDisplayed = false;
    private boolean inventoryStatusDisplayed = false;
    private String inventoryStatus = "";

    @Override
    public void displayMessage(String message) {
        messageDisplayed = true;
    }

    public boolean isMessageDisplayed() {
        return messageDisplayed;
    }

    @Override
    public void displayInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
        inventoryStatusDisplayed = true;
    }

    public boolean isInventoryStatusDisplayed() {
        return inventoryStatusDisplayed;
    }

    public String getDisplayedInventoryStatus() {
        return inventoryStatus;  // inventoryStatus 값을 반환
    }
}
