package tobyspring.hellospring;

/**
 * 오브젝트 만들기 + 의존관계 설정하기
 */
public class ObjectFactory {

    public PaymentService paymentService() {
        return new PaymentService(exRateProvider());
    }

    public ExRateProvider exRateProvider() {
        return new WebApiExRateProvider();
    }
}
