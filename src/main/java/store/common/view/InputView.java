package store.common.view;

import camp.nextstep.edu.missionutils.Console;
import store.common.exception.ErrorMessages;
import store.common.exception.InvalidFormatException;
import store.common.handler.InputRetryHandler;
import store.domain.inventory.factory.CartFactory;
import store.domain.inventory.model.Cart;

public class InputView {
    private static final String PROMPT_PRODUCT_QUANTITY = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String PROMPT_EXTRA_PROMOTION = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    private static final String PROMPT_REGULAR_PRICE_OPTION = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";
    private static final String PROMPT_MEMBERSHIP = "멤버십 할인을 받으시겠습니까? (Y/N)";
    private static final String PROMPT_CONTINUE_SHOPPING = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";
    private final InputRetryHandler inputRetryHandler;

    public InputView(InputRetryHandler inputRetryHandler) {
        this.inputRetryHandler = inputRetryHandler;
    }

    public Cart getProductNameAndQuantity() {
        return inputRetryHandler.execute(() -> {
            System.out.println(PROMPT_PRODUCT_QUANTITY);
            String input = Console.readLine();
            return CartFactory.createCartFromInput(input);
        });
    }

    public boolean confirmMembership() {
        return inputRetryHandler.execute(() -> getYesOrNo(PROMPT_MEMBERSHIP).equalsIgnoreCase("Y"));
    }

    public boolean confirmExtraPromotion(String productName) {
        return inputRetryHandler.execute(() -> getYesOrNo(String.format(PROMPT_EXTRA_PROMOTION, productName)).equalsIgnoreCase("Y"));
    }

    public boolean confirmRegularPriceOption(String productName, int quantity) {
        return inputRetryHandler.execute(() -> getYesOrNo(String.format(PROMPT_REGULAR_PRICE_OPTION, productName, quantity)).equalsIgnoreCase("Y"));
    }

    public boolean getContinueShoppingConfirmation() {
        return inputRetryHandler.execute(() -> getYesOrNo(PROMPT_CONTINUE_SHOPPING).equalsIgnoreCase("Y"));
    }

    private String getYesOrNo(String question) {
        System.out.println(question);
        String input = Console.readLine();
        if (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N")) {
            throw new InvalidFormatException(ErrorMessages.GENERIC_ERROR);
        }
        return input;
    }
}
