package store.common.exception;

public enum ErrorMessages {

    // 입력 형식 관련 오류
    INVALID_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    GENERIC_ERROR("잘못된 입력입니다. 다시 입력해 주세요."),

    // 상품 관련 오류
    NON_EXISTENT_PRODUCT("존재하지 않는 상품입니다. 다시 입력해 주세요."),

    // 재고 및 수량 관련 오류
    EXCEEDS_STOCK("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INVALID_QUANTITY("구매 수량이 올바르지 않습니다. 수량은 1 이상이어야 합니다."),
    NEGATIVE_QUANTITY("수량은 음수일 수 없습니다."),

    // 프로모션 관련 오류
    INVALID_PROMOTION("유효하지 않은 프로모션입니다. 다시 확인해 주세요."),
    INVALID_DATE("프로모션 날짜가 올바르지 않습니다. 시작일과 종료일을 확인해 주세요.");

    private static final String PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return PREFIX + message;
    }
}
