package store;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.common.exception.ErrorMessages;
import store.common.initializer.InventoryInitializer;
import store.extension.annotation.FixedDateTime;

import java.time.LocalDate;

import static camp.nextstep.edu.missionutils.test.Assertions.assertNowTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTest extends NsTest {

    @BeforeEach
    void setUp() {
        InventoryInitializer.getInstance().resetInventory();
    }

    @Test
    @FixedDateTime
    void 파일에_있는_상품_목록_출력() {
        assertSimpleTest(() -> {
            run("[물-1]", "N", "N");
            assertThat(output()).contains(
                    "- 콜라 1,000원 10개 탄산2+1",
                    "- 콜라 1,000원 10개",
                    "- 사이다 1,000원 8개 탄산2+1",
                    "- 사이다 1,000원 7개",
                    "- 오렌지주스 1,800원 9개 MD추천상품",
                    "- 오렌지주스 1,800원 재고 없음",
                    "- 탄산수 1,200원 5개 탄산2+1",
                    "- 탄산수 1,200원 재고 없음",
                    "- 물 500원 10개",
                    "- 비타민워터 1,500원 6개",
                    "- 감자칩 1,500원 5개 반짝할인",
                    "- 감자칩 1,500원 5개",
                    "- 초코바 1,200원 5개 MD추천상품",
                    "- 초코바 1,200원 5개",
                    "- 에너지바 2,000원 5개",
                    "- 정식도시락 6,400원 8개",
                    "- 컵라면 1,700원 1개 MD추천상품",
                    "- 컵라면 1,700원 10개"
            );
        });
    }

    @Test
    void 여러_개의_일반_상품_구매() {
        assertSimpleTest(() -> {
            run("[비타민워터-3],[물-2],[정식도시락-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈18,300");
        });
    }

    @Test
    void 기간에_해당하지_않는_프로모션_적용() {
        assertNowTest(() -> {
            run("[감자칩-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈3,000");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    @FixedDateTime
    void 예외_테스트() {
        assertSimpleTest(() -> {
            runException("[컵라면-12]", "N", "N");
            assertThat(output()).contains("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 특정_프로모션_적용_상품_구매() {
        assertSimpleTest(() -> {
            run("[콜라-3]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈2,000");
        });
    }

    @Test
    void MD추천상품_구매() {
        assertSimpleTest(() -> {
            run("[초코바-1]", "N", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈1,200");
        });
    }

    @Test
    void 여러_개의_할인_적용_상품_구매() {
        assertSimpleTest(() -> {
            run("[감자칩-2],[콜라-4]", "N", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈3,500");
        });
    }


    @Test
    void 여러_상품_정상_구매() {
        assertSimpleTest(() -> {
            run("[사이다-2],[오렌지주스-1],[물-4]", "N", "N", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈5,800");
        });
    }

    @Test
    void 프로모션_종료일_이후_구매() {
        assertNowTest(() -> {
            run("[탄산수-3]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈2,400");
        }, LocalDate.of(2024, 3, 1).atStartOfDay());
    }

    @Test
    void 잘못된_상품_코드_입력() {
        assertSimpleTest(() -> {
            runException("[없는상품-1]");
            assertThat(output()).contains(ErrorMessages.NON_EXISTENT_PRODUCT.getMessage());
        });
    }

    @Test
    void 싹쓸이_테스트() {
        assertSimpleTest(() -> {
            run("[오렌지주스-9]", "Y", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈9,000");
        });
    }

    @Test
    void 미래에_존재하는_프로모션_적용() {
        assertNowTest(() -> {
            run("[감자칩-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈1,500");
        }, LocalDate.of(2024, 11, 25).atStartOfDay());
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
