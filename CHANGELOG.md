### **Changelog**

---

#### **Features**
- **feat(utility):** DateTimeGenerator 구성 - KNU-K (2024-11-11)
- **feat(order-factory):** Order 도메인을 형성하는 OrderFactory 구성 - KNU-K (2024-11-11)
- **feat(PromotionStrategy):** 전략 패턴 추가 - KNU-K (2024-11-11)
- **feat(Application):** ConvenienceStoreManager를 동작하여 편의점 로직 수행 - KNU-K (2024-11-10)
- **feat(ConvenienceStore):** 각 컨트롤러들의 상호 작용을 통해서 편의점 로직 수행 - KNU-K (2024-11-10)
- **feat(receipt):** ReceiptFactory 패턴을 추가하여 Receipt 구성 - KNU-K (2024-11-10)
- **feat(Order):** Order 도메인을 구성하는 컨트롤러 및 서비스 추가 - KNU-K (2024-11-10)
- **feat(payment):** Payment를 구성하는 컨트롤러 및 서비스 추가 - KNU-K (2024-11-10)
- **feat(cart):** Cart를 만드는 CartFactory 추가 - KNU-K (2024-11-10)
- **feat(Protmotion):** Promotion 도메인의 서비스 추가 - KNU-K (2024-11-10)
- **feat(inventory):** Inventory 도메인의 컨트롤러 및 서비스 추가 - KNU-K (2024-11-10)
- **feat(view-OutputView):** OutputView 추가 - KNU-K (2024-11-10)
- **feat(view-InputView):** InputView 추가 - KNU-K (2024-11-10)
- **feat(initializer):** InventoryInitializer 구성(싱글 톤 패턴 추가) - KNU-K (2024-11-10)
- **feat(initializer):** Promotion, Inventory 정보 파싱 - KNU-K (2024-11-10)
- **feat(util):** fileReader를 통한 MD 읽는 유틸 추가 - KNU-K (2024-11-10)
- **feat(Price):** Price 도메인 추가 - KNU-K (2024-11-10)
- **feat(common):** baseController 추상화 클래스 구성 - KNU-K (2024-11-10)
- **feat(constant):** FileConstants 추가 (재고, 프로모션 파일 위치) - KNU-K (2024-11-10)
- **feat(util):** InputRetryHandler 유틸 추가 - KNU-K (2024-11-10)
- **feat(exception):** 기본적인 에러 기저 추가 - KNU-K (2024-11-10)
- **feat(Promotion):** 프로모션을 찾기 위한 팩토리 구성 - KNU-K (2024-11-10)
- **feat(Promotion):** 프로모션에 대한 도메인 형성 - KNU-K (2024-11-10)
- **feat(Receipt):** 영수증에 대한 도메인 형성 - KNU-K (2024-11-10)
- **feat(Stock):** 재고에 대한 도메인 형성 - KNU-K (2024-11-10)
- **feat(Cart):** 장바구니에 대한 도메인 형성 - KNU-K (2024-11-10)
- **feat(Order):** 주문에 대한 도메인 형성 - KNU-K (2024-11-10)
- **feat(Inventory):** 창고에 대한 도메인 형성 - KNU-K (2024-11-10)
- **feat(Payment):** 결제에 대한 도메인 형성 - KNU-K (2024-11-10)
- **feat(Product):** 제품 도메인 구성 - KNU-K (2024-11-10)

---

#### **Refactors**
- **refactor:** N+1 행사 상품에 대한 기저 추가 - KNU-K (2024-11-11)
- **refactor(order-service):** 코드 중복된 부분 제거 - KNU-K (2024-11-11)
- **refactor(promotion-strategy):** 프로모션 전략 코드 최적화 - KNU-K (2024-11-11)
- **refactor(initializer):** 파일 전처리 시 커스텀 에러에 추가 및 적용 - KNU-K (2024-11-11)
- **refactor(handler):** 메인 핸들러에서 Outputview를 사용하도록 재구성 - KNU-K (2024-11-11)
- **refactor(utility):** 변수 네이밍을 가독성 있게 확실하게 변경 - KNU-K (2024-11-11)
- **refactor(Promotion):** 메소드와 접근 제한자 리팩토링 - KNU-K (2024-11-11)
- **refactor(Payment):** 메소드와 접근 제한자 리팩토링 - KNU-K (2024-11-11)
- **refactor(Order):** 메소드와 접근 제한자 리팩토링 - KNU-K (2024-11-11)
- **refactor(inventory):** 메소드와 접근 제한자 리팩토링 - KNU-K (2024-11-11)
- **refactor(order):** callback 패턴을 활용하여 Controller 내에서 조건 분기로 인한 결합 완화 - KNU-K (2024-11-11)
- **refactor(order-controller):** OrderController 내에서 통일된 조건식의 메소드 분리 - KNU-K (2024-11-11)
- **refactor(promotion):** 정책 적용 조건 변경 - KNU-K (2024-11-11)
- **refactor(order):** order 도메인 내 일부 책임 이동 - KNU-K (2024-11-11)
- **refactor(application):** 싱글톤 초기화 로직 추가 - KNU-K (2024-11-10)
- **refactor(factory):** 팩토리 일부 기저 삭제 - KNU-K (2024-11-10)
- **refactor(Inventory):** 각 판별이 독립적으로 시행되도록 수정 - KNU-K (2024-11-10)
- **refactor(Inventory):** 재고양이 충분하지 판별하는 식 변경 - KNU-K (2024-11-10)
- **refactor(Inventory):** 프로모션 재고 우선적 차감 로직 추가 - KNU-K (2024-11-10)
- **refactor(payment-service):** 프로모션 전체를 포함하는 Price를 뱉는 메소드 수정 - KNU-K (2024-11-10)
- **refactor(order-controller):** 정가 구매 프롬프트 제외 기저 추가 - KNU-K (2024-11-10)
- **refactor(inventory-service):** 인벤토리 서비스 내에 validate 추가 - KNU-K (2024-11-11)

---

#### **Tests**
- **test:** unit test 진행 및 고정 시간에 대한 테스트 진행 - KNU-K (2024-11-11)
- **test:** InventoryExtension 주석을 명확하게 변경 - KNU-K (2024-11-11)
- **test(inventory):** 인벤토리, 가격, 제품 등 인벤토리를 구성하는 요소에 대한 AAA 테스트 진행 - KNU-K (2024-11-11)
- **test(order):** 주문에 대한 AAA 테스트 진행 - KNU-K (2024-11-11)
- **test(payment):** 결제, 결제 아이템, 영수증에 대한 AAA 테스트 진행 - KNU-K (2024-11-11)
- **test(promotion):** Promotion 및 PromotionPolicy에 대한 AAA 테스트 진행 - KNU-K (2024-11-11)
- **test(promotion):** 팩토리 클래스에 대한 테스트 진행 (고정된 시간에 대해서) - KNU-K (2024-11-11)
- **test(inventory):** InventoryExtension 추가 - KNU-K (2024-11-11)

---

#### **Docs**
- **docs(readme):** 리드미의 목차 수정 - KNU-K (2024-11-11)
- **docs(readme):** 리드미 내 추가 - KNU-K (2024-11-11)
- **docs(readme):** 도메인 설계 파트 정정 및 주요 로직에 대한 서술 추가 - KNU-K (2024-11-11)
- **docs(readme):** 도메인 설계 및 시스템 흐름 작성 - KNU-K (2024-11-10)

---

#### **Styles**
- **style(all):** 포맷팅 설정 및 불필요한 주석 제거 - KNU-K (2024-11-11)
- **style(all):** 포맷팅 재구성 - KNU-K (2024-11-11)
- **style(all):** 포맷팅 재구성 - KNU-K (2024-11-11)
- **style(all):** 포맷팅 설정 - KNU-K (2024-11-11)
- **style(order):** 포맷터 및 함수 위치 변경 - KNU-K (2024-11-11)
- **style(all):** formatter 적용 - KNU-K (2024-11