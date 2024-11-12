package tobyspring.hellospring;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tobyspring.hellospring.data.JdbcOrderRepository;
import tobyspring.hellospring.order.OrderRepository;
import tobyspring.hellospring.order.OrderService;
import tobyspring.hellospring.order.OrderServiceImpl;
import tobyspring.hellospring.order.OrderServiceTxProxy;

@Configuration
@Import(DataConfig.class)
@EnableTransactionManagement // @Transactional 붙은 부분에 transactional proxy 직접 만들도록
public class OrderConfig {
	@Bean
	public OrderRepository orderRepository(DataSource dataSource) {
		return new JdbcOrderRepository(dataSource);
	}

	@Bean
	public OrderService orderService(PlatformTransactionManager transactionManager,
		OrderRepository orderRepository) {
		return new OrderServiceTxProxy(new OrderServiceImpl(orderRepository, transactionManager), transactionManager);
	}
}
