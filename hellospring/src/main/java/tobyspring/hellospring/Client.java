package tobyspring.hellospring;

import java.math.BigDecimal;

public class Client {
    public static void main(String[] args) throws Exception{
        ObjectFactory objectFactory = new ObjectFactory();
        PaymentService paymentService = objectFactory.paymentService();
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println(payment);
    }
}
