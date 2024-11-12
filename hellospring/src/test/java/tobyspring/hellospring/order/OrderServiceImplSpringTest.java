package tobyspring.hellospring.order;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tobyspring.hellospring.OrderConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderConfig.class)
public class OrderServiceImplSpringTest {
	@Autowired
	OrderService orderServiceImpl;

	@Autowired
	DataSource dataSource;

	@Test
	void createOrder() {
		var order = orderServiceImpl.createOrder("0100", BigDecimal.ONE);
		assertThat(order.getId()).isGreaterThan(0);
	}

	@Test
	void createOrders(){
		List<OrderReq> orderReqs = List.of(new OrderReq("0200", BigDecimal.ONE), new OrderReq("0201", BigDecimal.TWO));
		var orders = orderServiceImpl.createOrders(orderReqs);

		assertThat(orders).hasSize(2);
		orders.forEach(order -> {
			assertThat(order.getId()).isGreaterThan(0);
		});
	}

	@Test
	void createDuplicateOrders(){
		List<OrderReq> orderReqs = List.of(
			new OrderReq("0200", BigDecimal.ONE),
			new OrderReq("0200", BigDecimal.TWO)
		);

		assertThatThrownBy(() -> orderServiceImpl.createOrders(orderReqs))
			.isInstanceOf(DataIntegrityViolationException.class);

		JdbcClient client = JdbcClient.create(dataSource);
		var count = client.sql("select count(*) from orders where no = '0200'").query(Long.class).single();
		assertThat(count).isEqualTo(0);
	}
}
