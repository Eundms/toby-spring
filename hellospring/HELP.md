# 섹션4. 테스트
## 테스트와 DI
- 스프링 DI를 이용하는 테스트
  - 테스트용 협력자(Collaborator)/의존 오브젝트를 스프링의 구성 정보를 이용해서 지정하고 컨테이너로부터 테스트 대상을 가져와서 테스트
  - `@ContextConfiguration`, `@Autowired`

# 섹션3. 오브젝트와 의존관계
## 의존성 역전 원칙(Dependency Inversion Principle)
- Dependency Injection으로 DIP 지킬 수 있음
```text
1. 상위 수준의 모듈은 하위 수준의 모듈에 의존해서는 안 된다. 둘 모두 추상화에 의존해야 한다.
2. 추상화는 구체적인 사항에 의존해서는 안 된다. 구체적인 사항은 추상화에 의존해야 한다.
```
- Policy Layer(PaymentService) : 상위 모듈, Mechanism Layer(WebApiExRateProvider) : 하위 모듈
  - 상위 모듈이 하위 모듈에 의존하게 되면, Mechanism Layer의 모듈이 변경된게 상위 모듈에 영향을 미칠 수 있음
  - PaymentService -> ExRateProvider <= WebApiExRateProvider 형태로는 상위가 하위에 의존하고 있어서 만족하지 않음
    - 이를 해결하기 위해 인터페이스 소유권의 역전이 필요하다 (`Separated Interface 패턴`)
      - 인터페이스는 자기를 구현하는 쪽 모듈이 아니라 사용하는 쪽(client)에 있는게 자연스러울 수 있음
        - Policy Layer : PaymentService -> ExRateProvider
        - Mechanism Layer : WebApiRateProvider
        
## DI와 디자인패턴 (1)
- 디자인패턴 분류 
  - 1. Purpose : 패턴 목적
  - 2. Scope : Class 패턴 - 상속 / Object 패턴 - 합성
    - Object 패턴 - 합성 : 오브젝트 2개가 런타임에 의존 관계를 맺도록 하자 
    - Object 패턴은 DI와 연관되어 있다 
- 환율 정보가 필요할 때 매번 WebAPI를 호출해야 할까?
  - 환율 정보가 필요한 기능이 증가함. 응답 시간이 빨라졌음 좋겠음. 환율 변동 주기가 바뀌지 않았음에도 조회해야 함?
  - => 환율 정보 캐시의 도입
- WebApiExRateProvider에 캐시 기능을 추가하려면?
  - WebApiExRateProvider 코드 수정 대신에, `데코레이터 디자인 패턴` 사용하여 기능 추가
- `데코레이터 디자인 패턴`
  - 오브젝트에 부가적인 기능/책임을 동적으로 부여
  - WebApiExRateProvider에 의존하는 CachedWebApiExRateProvider를 만듦
  - 데코레이터 특징은 기능을 추가하려는 애와 동일한 인터페이스 구현
- `ObjectFactory : 스프링이 사용하는 구성정보` 변경 만으로도 기존 코드 수정하지 않고 새로운 `캐시 기능`을 추가할 수 있음

## 싱글톤 레지스트리 : 스프링 빈 오브젝트는 단 한 개
- 단 하나의 객체만 존재하도록, public static 메소드 -> 전역적인 접근 일어남
```java
   PaymentService paymentService = beanFactory.getBean(PaymentService.class);
   PaymentService paymentService2 = beanFactory.getBean(PaymentService.class);
   System.out.println(paymentService2 == paymentService); // true
```
- `프록시` 기술 이용함 

## 구성 정보를 가져오는 다른 방법 : 컴포넌트 스캔
- 1) 어떤 클래스 이용해서 빈을 만들것인가?
- 2) 의존하는 클래스는?
=> 두가지 정보를 표현한다면 @Configuration 가 붙은 ObjectFactory가 없어도 됨
먼저, @Component : 프로그래밍이 실행되는 시점에 메타 프로그래밍 기법을 통해 스프링 빈 팩토리가 해당 에너테이션 정보를 데이터처럼 읽어올 수 있음
1. @Component를 빈이 필요한 클래스에 단다
2. @Configuration이 필요한 정보를 Scan하도록 진입점(@ComponentScan)을 지정한다 

## 스프링 컨테이너와 의존관계 주입
- 스프링 컨테이너 : IoC, DI 지원
- BeanFactory : application 구성하는 핵심클래스의 object를 가지고 있는 것 (우리가 만든 ObjectFactory가 BeanFactory 참고할 수 있게 함)
  - 1) Bean 클래스 : PaymentService, WebApiExRateProvider
  - 2) 의존 관계 : PaymentService 가 다양한 ExRateProvider 중에 WebApiExRateProvider를 사용함
  - 두가지 `구성 정보`를 가지고 있음
- 구현
  - AnnotationConfigApplicationContext에 @Configuration 이 붙은 ObjectFactory 를 넘긴다
  - getBean(클래스이름)하여 빈을 가져온다
- BeanFactory == `스프링 IoC/DI 컨테이너`
  - 왜 `제어권 역전(IoC)`, `의존 관계 주입(DI)`인가? 
    - `IoC` : PaymentService가 가지고 있던 제어권 (`어떤 오브젝트를 의존해서 사용할꺼야 라는 설정 책임`)을 BeanFactory가 대신해주기 때문
    - `DI` : PaymentService입장에서 WebApiExiRateProvider가 Dependency인데, 해당 Dependency를 생성자를 통해 PaymentService에 전달해줌
  - `컨테이너` : BeanFactory 처음만들어질 때 내부에 이미 필요한 객체를 만들어놨기 때문에, getBean을 통해 객체를 반환받을 수 있음
    - 왜 미리 만들어서 가지고 있는가?
      - 서버 애플리케이션에 많은 요청이 들어올 때마다 PaymentService를 new로 새로 생성하게 되면 부하가 많이 걸린다 
      - 상태 필드에 WebApiExRateProvider 정보를 가지고 있지 않기 떄문에 동시에 여러 스레드가 사용해도 괜찮다
      - 따라서, 미리 만들어두고 리턴함
      - 서블릿 컨테이너도 오브젝트를 미리 만들어두고 사용함

## 객체지향 설계 원칙과 디자인 패턴
- 관심사의 분리 
- 개방 폐쇄 원칙(Open-Closed Principle) : 확장에는 열려있고 변경에는 닫혀있어야 한다
  - 클래스의 기능을 확장할 때, 코드는 변경되면 안된다
  - PaymentService는 환율정보를 가져오는 방식을 확장할 수 있음. 하지만, 코드의 변경은 일어나지 않음. => `전략패턴`에 잘 적용되어 있음
- 높은 응집도와 낮은 결합도(High Coherence and low coupling) 
  - 하나의 모듈이 하나의 책임, 관심사에 집중되어 있다
  - 응집도가 높으면 변화가 일어날 떄 비용이 낮아짐
  - 응집도가 낮으면 코드 영향도에 대해 검증을 해야 함 
- 전략패턴
  - 자신의 기능 맥락(Context)에서 필요에 따라 변경이 필요한 알고리즘을 인터페이스를 통해 통째로 외부로 분리시키고, 이를 구현한 구체적인 알고리즘 클래스를 필요에 따라 바꿔서 사용할 수 있게 하는 디자인 패턴
    - interface 두고 위 아래의 데이터 두기 
  - java : sort
  ```java
    List<String> scores = Arrays.asList("a", "b", "c");
    Collections.sort(scores, (o1,o2)-> o1.length() - o2.length());
  ```
- 제어의 역전 
  - 제어권 이전을 통한 제어 관계 역전 - 프레임워크의 기본 동작 원리

## 오브젝트 팩토리
- `Client 관심사 분리` : 두개의 관심사를 가진 클래스가 되었다 
  - 1) PaymentService 이용하는 클라이언트 대표 ex Controller, API handler 역할
  - 2) PaymentService 와 ExRateProvider 둘의 의존관계 설정 책임
- 이를 해결하기 위해, PaymentService와 ExRateProvider의 의존관계를 설정하고 오브젝트를 만드는 책임을 가지는 ObjectFactory 클래스를 만든다

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
