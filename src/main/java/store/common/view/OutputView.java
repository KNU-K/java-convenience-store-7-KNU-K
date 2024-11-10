package store.common.view;

public class OutputView {
    public void displayInventoryStatus(String inventoryStatus) {
        displayMessage("안녕하세요. W편의점입니다.");
        displayMessage(System.lineSeparator());
        displayMessage("현재 보유하고 있는 상품입니다.");
        displayMessage(System.lineSeparator());
        displayMessage(inventoryStatus);
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
