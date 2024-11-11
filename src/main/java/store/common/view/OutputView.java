package store.common.view;

public class OutputView {

    private static final String GREETING_MESSAGE = "안녕하세요. W편의점입니다.";
    private static final String INVENTORY_STATUS_MESSAGE = "현재 보유하고 있는 상품입니다.";
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public void displayInventoryStatus(String inventoryStatus) {
        displayMessage(GREETING_MESSAGE);
        displayMessage(INVENTORY_STATUS_MESSAGE);
        displayMessage(LINE_SEPARATOR);
        displayMessage(inventoryStatus);
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
