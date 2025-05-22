# TIL

## Domain Model


![제목 없는 다이어그램 drawio](https://github.com/user-attachments/assets/94e9e6a2-eb9d-47b4-a2e2-fea65c68524c)

|영역|설명|
|-----|-------|
|사용자 인터페이스 또는 표현|사용자의 요청을 처리하고 사용자에게 정보를 보여준다. 여기서 사용자는 소프트웨어를 사용하는 사람뿐만 아니라 외부 시스템일 수도 있다.|
|응용|사용자가 요청한 기능을 실행한다. 업무 로직을 직접 구현하지 않으며, 도메인 계층 조합에서 기능을 실행한다.|
|도메인|시스템이 제공할 도메인 규칙을 구현한다.|
|인프라스트럭처| 데이터베이스나 메시징 시스템과 같은 외부 시스템과의 연동을 처리한다.|

### 🔥 도메인 계층은 도메인의 핵심 규칙을 구현한다.
OrderState -> isShippingChangeable() : PAYMENT_WARING,PREPARING = true  
- OrderState는 '주문 대기 중이거나 상품 준비 중에는 배송지를 변경할 수 있다' 도메인 규칙 구현함

### 🔥 벨류 타입은 불변으로 구현한다.
- Money가 불변 객체가 아니라면, price 파라미터가 변경될 때 발생하는 문제를 방지하기 위해 데이터를 복사한 새로운 객체를 생성해야 한다.
- Money가 불변 객체이면 Money의 데이터를 바꿀 수가 없기 때문에 파라미터로 전달 받은 price를 안전하게 사용할 수 있다.

### 🔥 set 메서드로 데이터를 전달하도록 구현하면 온전하지 않은 상태가 될 수 있다.
##### set 메서드로 필요한 모든 값을 전달해야 함
- order.setOrderLine(lines);
- order.setShippingInfo(shippingInfo);

##### orderer를 설정하지 않은 상태에서 주문 완료 처리
- order.setState(OrderState.PREPARING);

##### 도메인 객체를 불완전한 상태로 사용되는 것을 막으려면 생성 시점에 필요한 것을 전달해 주어야 한다. 즉 생성자를 통해 필요한 데이터를 모두 받아야 함

- Order order = new Order(orderer, lines, shippingInfo, OrderState.PREPARING);

### 🔥 최대한 도메인 용어를 사용해서 도메인 규칙을 코드로 작성
- 의미를 변환하는 과정에서 발생하는 버그도 줄고, 의미를 다시 해석하는 과정도 생략 됨
