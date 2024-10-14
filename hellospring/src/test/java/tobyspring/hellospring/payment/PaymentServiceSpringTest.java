package tobyspring.hellospring.payment;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tobyspring.hellospring.TestObjectFactory;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestObjectFactory.class)
public class PaymentServiceSpringTest {
	@Autowired PaymentService paymentService;
	@Autowired ExRateProviderStub exRateProviderStub;

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

}
