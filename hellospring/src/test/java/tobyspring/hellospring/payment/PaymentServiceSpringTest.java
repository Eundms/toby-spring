package tobyspring.hellospring.payment;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tobyspring.hellospring.TestPaymentConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestPaymentConfig.class)
public class PaymentServiceSpringTest {
	@Autowired PaymentService paymentService;
	@Autowired ExRateProviderStub exRateProviderStub;
	@Autowired Clock clock;

	@Test
	void prepare() throws Exception {
		// exRate : 1000
		Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

		// isEqualByComparingTo : BigDecimal은 유효자리수도 가지고 있기 때문에 isEqualTo 대신에 아래 사용
		assertThat(payment.getExRate()).isEqualByComparingTo(BigDecimal.valueOf(11_000));
		assertThat(payment.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(11_0000));

		// exRate : 500
		exRateProviderStub.setExRate(BigDecimal.valueOf(500));
		Payment payment2 = paymentService.prepare(1L, "USD", BigDecimal.TEN);

		// isEqualByComparingTo : BigDecimal은 유효자리수도 가지고 있기 때문에 isEqualTo 대신에 아래 사용
		assertThat(payment2.getExRate()).isEqualByComparingTo(BigDecimal.valueOf(500));
		assertThat(payment2.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(5000));

	}


	@Test
	void validUntil() throws Exception {
		Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

		// valid until이 prepare() 30분 뒤로 설정되었는가?
		LocalDateTime now = LocalDateTime.now(this.clock);
		LocalDateTime expectedValidUntil = now.plusMinutes(30);

		Assertions.assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);
	}


}
