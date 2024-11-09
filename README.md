# 🧑‍💻우아한 테크 프리코스 4주차 - 편의점

## 도메인 설계

도메인 설계에서 각 클래스의 설명을 아래와 같이 작성할 수 있습니다.

| 도메인 명 | 클래스 명            | 설명                                                                                                                                                       |
|-------|------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------|
| **상품**    | `Product`        | 상품의 기본 정보(상품명, 가격)를 관리하는 클래스입니다. 상품명과 가격을 기반으로 상품을 식별하고, 가격 계산 및 출력 기능을 제공합니다.                          |
| **재고**    | `Stock`          | 특정 상품의 재고를 관리하는 클래스입니다. 재고 수량을 저장하고, 재고를 업데이트할 수 있는 기능을 제공하며, 상품이 품절인지 여부를 확인하는 기능도 포함됩니다.  |
| **프로모션 재고** | `PromotionStock` | 프로모션에 따라 특별한 조건을 가진 재고를 관리하는 클래스입니다. 기존 `Stock` 클래스의 기능에 프로모션 정보가 추가되어, 프로모션에 해당하는 상품에 대해 수량 관리와 조건 확인 기능을 제공합니다. |
| **재고 관리** | `Inventory`      | 모든 상품의 재고와 프로모션 재고를 관리하는 클래스입니다. 상품 추가 및 재고 감소 기능을 포함하며, 재고가 부족한 경우 처리를 할 수 있는 로직을 제공합니다.         |
| **장바구니** | `Cart`           | 사용자가 장바구니에 담은 상품들을 관리하는 클래스입니다. 장바구니에 상품을 추가하고, 장바구니 내 상품들을 스트림 형태로 조회할 수 있는 기능을 제공합니다.           |
| **장바구니 항목** | `CartItem`       | 장바구니에 담긴 개별 상품 항목을 나타내는 클래스입니다. 각 항목은 상품명과 수량을 가지고 있으며, 수량을 증가/감소시키는 기능을 제공합니다.                       |
| **가격**    | `Price`          | 상품의 가격을 표현하는 클래스입니다. 가격 계산을 위한 다양한 연산(곱셈, 덧셈, 백분율 적용 등)을 제공하며, 가격을 문자열 형식으로 출력할 수 있는 기능도 포함됩니다. |
| **프로모션**  | `Promotion`      | 특정 상품에 적용할 수 있는 프로모션을 정의하는 클래스입니다. 프로모션 기간, 조건 등을 관리하고, 프로모션이 유효한지 여부를 확인할 수 있는 기능을 제공합니다.       |
| **영수증**   | `Receipt`        | 고객의 구매 내역을 출력하는 클래스입니다. 구매 상품과 증정 상품 목록, 총액, 할인 금액 등을 계산하여 영수증 형태로 출력하는 기능을 제공합니다.                   |
| **주문**              | `Order`             | 고객의 주문 정보를 관리하는 클래스입니다. 주문에 포함된 상품 목록과 총 결제 금액, 멤버십 여부를 포함하며, 주문 항목들을 스트리밍하거나 각 항목에 대해 작업을 할 수 있는 기능을 제공합니다.                                      |
| **주문 항목**         | `OrderItem`         | 각 주문 항목을 관리하는 클래스입니다. 상품 이름, 가격, 수량, 프로모션 수량 및 프로모션 정책을 포함하며, 장바구니 항목을 주문 항목으로 변환할 수 있는 생성자를 포함합니다.                                               |
| **결제**              | `Payment`           | 주문에 대한 결제 정보를 관리하는 클래스입니다. 결제 항목 리스트, 총 결제 금액, 멤버십 할인 금액, 프로모션 할인 금액 등을 포함하여 결제 계산을 담당합니다.                                                                  |
| **결제 항목**          | `PaymentItem`       | 개별 결제 항목을 관리하는 클래스입니다. 상품 이름, 수량, 증정품 수량, 원래 금액, 프로모션 할인 금액 등을 포함하며, 각 결제 항목에 대한 정보를 제공합니다.                                                                   |
| **프로모션**           | `Promotion`         | 프로모션의 이름과 정책을 관리하는 클래스입니다. 프로모션이 유효한지 확인하고, 특정 수량을 구매했을 때 추가 상품을 받을 수 있는지 확인하는 기능을 제공합니다.                                                                 |
| **프로모션 정책**      | `PromotionPolicy`   | 프로모션의 조건을 정의하는 클래스입니다. 구매 수량, 증정품 수량, 프로모션 유효 기간 등을 포함하며, 프로모션이 유효한지 확인하고, 구매 수량에 따라 증정품 수량을 계산하는 로직을 구현합니다. |

## 시스템 흐름
1. 파일 읽기
   - `promotions.md` 를 읽어서 PromotionFactory 를 구성하여, 이름을 통해 Promotion을 읽을 수 있도록한다.
   - `products.md` 를 읽어서 inventory 를 구성한다. PromotionFactory를 통해 Promotion을 입력받는다.
2. 카트에 상품 담기
3. 담은 상품을 통해서 주문하기
    - 프로모션 적용이 가능한 상품에 대해 해당 수량보다 적게 가져온 경우 그 만큼 추가
    - 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택없이 결제해야하는 경우 정가로 결제할지에 대한 판별
    - 멤버십 할인 적용여부를 입력
4. 주문을 통해서 결제하기
5. 결제를 통해서 영수증 생성