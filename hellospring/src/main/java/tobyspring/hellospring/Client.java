package tobyspring.hellospring;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import tobyspring.hellospring.payment.Payment;
import tobyspring.hellospring.payment.PaymentService;

public class Client {
    public static void main(String[] args) throws Exception{
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(PaymentConfig.class);
        PaymentService paymentService = beanFactory.getBean(PaymentService.class);

        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("Payment1: " + payment);
        System.out.println("==================");

        Payment payment2 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("Payment2: " + payment2);

        System.out.println("==================");
        TimeUnit.SECONDS.sleep(3);
        Payment payment3 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("Payment3: " + payment3);

    }
}
