package store.domain.inventory.factory;

import store.common.constants.ErrorMessages;
import store.common.exception.InvalidFormatException;
import store.common.exception.InvalidInventoryException;
import store.common.initializer.InventoryInitializer;
import store.domain.inventory.model.Cart;
import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Inventory;

import java.util.Arrays;

public class CartFactory {

    private static final String VALID_INPUT_PATTERN = "^\\[([^\\s,]+-\\d+)\\](,\\[([^\\s,]+-\\d+)\\])*$";
    private static final String REGEX_PATTERN = "\\],\\s*\\[";
    private static final String CLEAN_UP_PATTERN = "[\\[\\] ]";
    private static final String CHECK_POSITIVE_INTEGER_PATTERN = "^[1-9]\\d*$";
    private static final String ITEM_SEPARATOR = ",";
    private static final String DATA_SEPARATOR = "-";

    public static Cart createCartFromInput(String input) {
        validateInputFormat(input);
        Cart cart = new Cart();
        String[] itemGroups = cleanAndSplitInput(input);

        Arrays.stream(itemGroups)
                .map(CartFactory::splitItemGroup)
                .forEach(itemDataArray -> processItemData(itemDataArray, cart));

        return cart;
    }

    private static void validateInputFormat(String input) {
        if (!input.matches(VALID_INPUT_PATTERN)) {
            throw new InvalidFormatException(ErrorMessages.INVALID_FORMAT);
        }
    }

    private static void validateExistProduct(String productName) {
        Inventory inventory = InventoryInitializer.getInstance().getInventory();
        if (!inventory.isExistProduct(productName)) {
            throw new InvalidInventoryException(ErrorMessages.NON_EXISTENT_PRODUCT);
        }
    }

    private static String[] cleanAndSplitInput(String input) {
        String cleanedInput = input.replaceAll(CLEAN_UP_PATTERN, "");
        return cleanedInput.split(REGEX_PATTERN);
    }

    private static String[] splitItemGroup(String itemGroup) {
        return itemGroup.split(ITEM_SEPARATOR);
    }

    private static void processItemData(String[] itemDataArray, Cart cart) {
        Arrays.stream(itemDataArray).map(CartFactory::parseItemData).forEach(cart::addItem);
    }

    private static CartItem parseItemData(String itemData) {
        String[] itemDetails = itemData.split(DATA_SEPARATOR);
        validateExistProduct(itemDetails[0]);
        String itemName = itemDetails[0];

        validateProductQuantity(itemDetails[0], itemDetails[1]);
        int itemQuantity = Integer.parseInt(itemDetails[1]);
        return new CartItem(itemName, itemQuantity);

    }

    private static void validateProductQuantity(String name, String itemQuantity) {
        Inventory inventory = InventoryInitializer.getInstance().getInventory();
        if (!itemQuantity.matches(CHECK_POSITIVE_INTEGER_PATTERN)) {
            throw new InvalidFormatException(ErrorMessages.INVALID_QUANTITY);
        }

        if (!inventory.isAvailableQuantity(name, Integer.parseInt(itemQuantity))) {
            throw new InvalidFormatException(ErrorMessages.EXCEEDS_STOCK);
        }

    }
}
