package store.domain.inventory.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @ParameterizedTest
    @CsvSource({
            "'테스트 제품', 1000, '테스트 제품 1,000원'",
            "'제품 A', 2000, '제품 A 2,000원'",
            "'제품 B', 5000, '제품 B 5,000원'"
    })
    void 상품_정보를_다양한_값으로_올바르게_출력한다(String productName, int price, String expectedOutput) {
        // Arrange
        Price productPrice = new Price(price);
        Product product = new Product(productName, productPrice);

        // Act
        String result = product.toString();

        // Assert
        assertEquals(expectedOutput, result);
    }
}
