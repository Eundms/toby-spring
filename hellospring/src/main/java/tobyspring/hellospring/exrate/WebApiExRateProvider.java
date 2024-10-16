package tobyspring.hellospring.exrate;

import java.math.BigDecimal;

import tobyspring.hellospring.api.ApiTemplate;
import tobyspring.hellospring.api.ErApiExtractor;
import tobyspring.hellospring.payment.ExRateProvider;

public class WebApiExRateProvider implements ExRateProvider {
	private final ApiTemplate apiTemplate;

	public WebApiExRateProvider(ApiTemplate apiTemplate) {
		this.apiTemplate = apiTemplate;
	}

	@Override
	public BigDecimal getExRate(String currency) {
		String url = "https://open.er-api.com/v6/latest/" + currency;

		return apiTemplate.getExRate(url);
	}

}
