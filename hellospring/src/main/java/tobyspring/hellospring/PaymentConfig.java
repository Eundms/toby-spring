package tobyspring.hellospring;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tobyspring.hellospring.api.ApiTemplate;
import tobyspring.hellospring.exrate.CachedExRateProvider;
import tobyspring.hellospring.exrate.WebApiExRateProvider;
import tobyspring.hellospring.payment.ExRateProvider;
import tobyspring.hellospring.payment.PaymentService;

/**
 * 오브젝트 만들기 + 의존관계 설정하기
 */
@Configuration
public class PaymentConfig {
	@Bean
	public PaymentService paymentService() {
		return new PaymentService(exRateProvider(), clock());
	}

	@Bean
	public ExRateProvider cachedExRateProvider() {
		return new CachedExRateProvider(exRateProvider());
	}

	@Bean
	public ApiTemplate apiTemplate(){
		return new ApiTemplate();
	}

	@Bean
	public ExRateProvider exRateProvider() {
		return new WebApiExRateProvider(apiTemplate());
	}

	@Bean
	public Clock clock() {
		return Clock.systemDefaultZone();
	}

}
