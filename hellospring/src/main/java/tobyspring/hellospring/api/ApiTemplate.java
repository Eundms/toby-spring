package tobyspring.hellospring.api;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;

import com.fasterxml.jackson.core.JsonProcessingException;

import tobyspring.hellospring.exrate.ExrateExtractor;
import tobyspring.hellospring.exrate.HttpClientApiExecutor;

public class ApiTemplate {
	private final ApiExecutor apiExecutor;
	private final ExrateExtractor exrateExtractor;

	public ApiTemplate() {
		this.apiExecutor = new HttpClientApiExecutor();
		this.exrateExtractor = new ErApiExtractor();
	}

	public ApiTemplate(SimpleApiExecutor simpleApiExecutor, ErApiExtractor erApiExtractor) {
		this.apiExecutor = simpleApiExecutor;
		this.exrateExtractor = erApiExtractor;
	}

	public BigDecimal getExRate(String url) {
		return this.getExRate(url, this.apiExecutor, this.exrateExtractor);
	}

	public BigDecimal getExRate(String url, ApiExecutor apiExecutor) {
		return this.getExRate(url, apiExecutor, this.exrateExtractor);
	}

	public BigDecimal getExRate(String url, ApiExecutor apiExecutor, ExrateExtractor exrateExtractor) { // 템플릿 패턴
		URI uri;
		try {
			uri = new URI(url);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		String response = "";
		try {
			response = apiExecutor.execute(uri);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		try {
			return exrateExtractor.extract(response);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
