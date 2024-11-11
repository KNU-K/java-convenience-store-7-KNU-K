package store.common.constants;

public enum FileConstants {
    PROMOTIONS_FILE_PATH("promotions.md"),
    PRODUCTS_FILE_PATH("products.md");
    private final String type;

    FileConstants(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}

