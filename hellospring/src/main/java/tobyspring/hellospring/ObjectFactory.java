package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 오브젝트 만들기 + 의존관계 설정하기
 */
@Configuration
// @ComponentScan
public class ObjectFactory {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(cachedExRateProvider());
    }

	@Bean
	public ExRateProvider cachedExRateProvider() {
		return new CachedExRateProvider(exRateProvider());
	}

    @Bean
    public ExRateProvider exRateProvider() {
        return new WebApiExRateProvider();
    }

}
