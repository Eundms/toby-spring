# 섹션3. 오브젝트와 의존관계
## 오브젝트 팩토리
- `Client 관심사 분리` : 두개의 관심사를 가진 클래스가 되었다 
  - 1) PaymentService 이용하는 클라이언트 대표 ex Controller, API handler 역할
  - 2) PaymentService 와 ExRateProvider 둘의 의존관계 설정 책임
- 이를 해결하기 위해, PaymentService와 ExRateProvider의 의존관계를 설정하고 오브젝트를 만드는 책임을 가지는 ObjectFactory 클래스를 만든다
- 

## 관계 설정 책임의 분리 
- 관계 설정 책임을 PaymentService가 아닌 Client로 옮기는 작업
  - 이를 위해, PaymentService의 생성자로 객체를 받았다 (생성자 주입)
## 인터페이스 도입
- XXXProvider가 ExRateProvider(interface) 를 구현하도록 하자
- 한계
  - 1) PaymentService 가 변경됨 (생성자에 new XXProvider()이렇게 관계 설정이 되어있다)
  - 코드레벨 의존관계, 런타임 의존관계은 다름 
    - 관계 설정 책임에 따라 코드레벨 의존관계도 달라진다
    - 즉, 관계 설정 책임을 PaymentService가 아닌 Client로 옮기는 작업을 진행함으로써, 의존관계를 없애자

## 클래스의 분리
- SimpleExRateProvider, WebApiExRateProvider로 상속 제거 
- WebApiExRateProvider가 여러번 생성되지 않게 하기 : 생성자에서 new함
- 문제) 메소드 이름이 다 다르면 xxPriovider를 변경할 때 마다 메소드(getSimple, getExRate)를 다 변경해야 하는 문제가 생김

## 상속을 통한 확장
- 목표 : 환율 정보를 가져오는 함수를 PaymentService 클래스에서 분리하기 => (getExRate) 를 내마음대로 커스텀해서 쓰고 싶음!
- 찾아보기) 상속으로 유연하게 만드는 디자인 패턴 : `템플릿 메소드 패턴`, `팩토리 메소드 패턴` 
- 한계 
  - 1) 상위 클래스에 함수를 더 추가한다고하자.-> 상속하는 클래스들이 2개의 메소드를 만들어야 함 -> 수많은 구현 클래스를 변경해야 함
  - 2) 다중 상속 불가능  
## 관심사의 분리
- 잘못된 코멘트는 오히려 독이다 (커멘트 삭제하다가 데이터 삭제할 수도 있음)
- `관심사의 분리 (Separation of Concerns SoC)`
   - 미래의 나를 믿지 말자

- 기술 로직 + 서비스 로직이 혼재되어 있음
- 관심사 분리 == 변경의 이유, 변경의 시점에 따라 분리하기
- ctrl + Alt + m : 메소드 추출

## 오브젝트와 의존관계
- 클래스 = 설계도
- 클래스의 인스턴스 = 오브젝트
- 의존관계 = A ---> B A는 B에 의존한다 
  - 내가 의존하고 있는 클래스가 변경되면 나도 영향을 받는다 

# 섹션2. 스프링 개발 시작하기 
1. Long vs Integer
2. java 접근 제한자
- 패키지에서 수정할 수 없게 
3. 금액에 대한 처리 
- Double, Float : 부동소수점, 오차가 생길 수 있음
- 그러므로, BigDecimal 사용하기
4. LocalDateTime

5. 생성자 사용 단점
- 같은 타입이 연속으로 나오는 경우 실수할 수 있음
- 그러므로, builder 사용하기

6. java io
   InputStream
   ctrl + alt + v :

7. jackson : json <-> class
- @JsonIgnoreProperties(ignoreUnknown = true) : json 모르는 값 무시


8. java record
- 불변 클래스
