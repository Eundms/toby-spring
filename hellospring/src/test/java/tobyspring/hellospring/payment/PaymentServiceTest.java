package tobyspring.hellospring.payment;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class PaymentServiceTest {
	@Test
	void prepare() throws Exception {
        testAmount(BigDecimal.valueOf(500), BigDecimal.valueOf(5000));
		testAmount(BigDecimal.valueOf(1000), BigDecimal.valueOf(10000));
		testAmount(BigDecimal.valueOf(3000), BigDecimal.valueOf(30000));

		// // 환율정보 가져온다
		// assertThat(payment.getExRate()).isNotNull();
		//
		// // 원화 환산 금액 계산
		// assertThat(payment.getConvertedAmount())
		// 	.isEqualTo(payment.getExRate().multiply(payment.getForeignCurrencyAmount()));
		//
		// // 원화환산금액의 유효시간 계산
		// assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
		// assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
	}

	private static void testAmount(BigDecimal exRate, BigDecimal convertedAmount) throws Exception {
		PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate));

		Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

		// isEqualByComparingTo : BigDecimal은 유효자리수도 가지고 있기 때문에 isEqualTo 대신에 아래 사용
		assertThat(payment.getExRate()).isEqualByComparingTo(exRate);
		assertThat(payment.getConvertedAmount()).isEqualTo(convertedAmount);
	}
}
