package tobyspring.hellospring;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import tobyspring.hellospring.api.ApiTemplate;
import tobyspring.hellospring.api.ErApiExtractor;
import tobyspring.hellospring.api.SimpleApiExecutor;
import tobyspring.hellospring.exrate.CachedExRateProvider;
import tobyspring.hellospring.exrate.RestTemplateExRateProvider;
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
	public RestTemplate restTemplate(){
		return new RestTemplate(new JdkClientHttpRequestFactory());
	}

	@Bean
	public ApiTemplate apiTemplate(){
		return new ApiTemplate(new SimpleApiExecutor(), new ErApiExtractor());
	}

	@Bean
	public ExRateProvider exRateProvider() {
		return new RestTemplateExRateProvider(restTemplate());
	}

	@Bean
	public Clock clock() {
		return Clock.systemDefaultZone();
	}

}
