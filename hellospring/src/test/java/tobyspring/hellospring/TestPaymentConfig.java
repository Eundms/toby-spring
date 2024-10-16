package tobyspring.hellospring;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tobyspring.hellospring.payment.ExRateProvider;
import tobyspring.hellospring.payment.ExRateProviderStub;
import tobyspring.hellospring.payment.PaymentService;

/**
 * 오브젝트 만들기 + 의존관계 설정하기
 */
@Configuration
public class TestPaymentConfig {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider(), clock());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new ExRateProviderStub(BigDecimal.valueOf(1_1000));
    }

    @Bean
    public Clock clock(){
        return Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

}
