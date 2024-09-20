package tobyspring.hellospring;

import java.math.BigDecimal;

public class Client {
    public static void main(String[] args) throws Exception{
        PaymentService paymentService= new PaymentService();
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println(payment);
    }
}
