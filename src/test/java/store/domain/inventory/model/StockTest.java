package store.domain.inventory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


class StockTest {

    @ParameterizedTest
    @CsvSource({
            "'테스트 제품', 1000, 10, '- 테스트 제품 1,000원 10개'",
            "'제품 A', 2000, 0, '- 제품 A 2,000원 재고 없음'",
            "'제품 B', 5000, 5, '- 제품 B 5,000원 5개'"
    })
    void 상품_재고를_다양한_값으로_올바르게_출력한다(String productName, int price, int quantity, String expectedOutput) {
        // Arrange
        Price productPrice = new Price(price);
        Product product = new Product(productName, productPrice);
        Stock stock = new Stock(product, quantity);

        // Act
        String result = stock.toString();

        // Assert
        assertEquals(expectedOutput, result);
    }

    @Test
    void 상품이름과_일치하는지_확인한다() {
        // Arrange
        Price productPrice = new Price(1000);
        Product product = new Product("테스트 제품", productPrice);
        Stock stock = new Stock(product, 10);

        // Act & Assert
        assertTrue(stock.isProductNameEqual("테스트 제품"));
        assertFalse(stock.isProductNameEqual("다른 제품"));
    }

    @Test
    void 재고수량_수정이_반영되는지_확인한다() {
        // Arrange
        Price productPrice = new Price(1000);
        Product product = new Product("테스트 제품", productPrice);
        Stock stock = new Stock(product, 10);

        // Act
        stock.updateQuantity(20);

        // Assert
        assertEquals(20, stock.getQuantity());
    }
}

