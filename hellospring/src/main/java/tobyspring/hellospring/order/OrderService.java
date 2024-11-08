package tobyspring.hellospring.order;

import java.math.BigDecimal;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class OrderService {
	private final OrderRepository jpaOrderRepository;
	private final PlatformTransactionManager transactionManager;

	public OrderService(OrderRepository jpaOrderRepository, PlatformTransactionManager transactionManager) {
		this.jpaOrderRepository = jpaOrderRepository;
		this.transactionManager = transactionManager;
	}

	public Order createOrder(String no, BigDecimal total) {
		Order order = new Order(no, total);

		return new TransactionTemplate(transactionManager).execute(status -> {
			this.jpaOrderRepository.save(order);
			return order;
		});
	}
}
