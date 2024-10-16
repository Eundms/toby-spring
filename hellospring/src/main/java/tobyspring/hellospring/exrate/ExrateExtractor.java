package tobyspring.hellospring.exrate;

import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface ExrateExtractor {
	BigDecimal extract(String response) throws JsonProcessingException;
}
