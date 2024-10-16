package tobyspring.hellospring.payment;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PaymentServiceTest {
	Clock clock ;

	@BeforeEach
	void beforeEach(){
		this.clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
	}

	@Test
	void prepare() throws Exception {
        testAmount(BigDecimal.valueOf(500), BigDecimal.valueOf(5000), this.clock);
		testAmount(BigDecimal.valueOf(1000), BigDecimal.valueOf(10000),this.clock);
		testAmount(BigDecimal.valueOf(3000), BigDecimal.valueOf(30000),this.clock);
	}

	@Test
	void validUntil() throws Exception {
		PaymentService paymentService = new PaymentService(new ExRateProviderStub( BigDecimal.valueOf(5000)),clock);
		Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

		// valid until이 prepare() 30분 뒤로 설정되었는가?
		LocalDateTime now = LocalDateTime.now(this.clock);
		LocalDateTime expectedValidUntil = now.plusMinutes(30);

		Assertions.assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);
	}

	private static void testAmount(BigDecimal exRate, BigDecimal convertedAmount, Clock clock) throws Exception {
		PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate),clock);

		Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

		// isEqualByComparingTo : BigDecimal은 유효자리수도 가지고 있기 때문에 isEqualTo 대신에 아래 사용
		assertThat(payment.getExRate()).isEqualByComparingTo(exRate);
		assertThat(payment.getConvertedAmount()).isEqualTo(convertedAmount);
	}
}
