package tobyspring.hellospring;

import java.math.BigDecimal;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import tobyspring.hellospring.data.OrderRepository;
import tobyspring.hellospring.order.Order;

public class DataClient {
	public static void main(String[] args) {
		BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
		OrderRepository orderRepository = beanFactory.getBean(OrderRepository.class);
		JpaTransactionManager transactionManager = beanFactory.getBean(JpaTransactionManager.class);
		try {
			new TransactionTemplate(transactionManager).execute(status -> {
				// transcation begin
				Order order = new Order("2", BigDecimal.TEN);
				orderRepository.save(order);

				Order order2 = new Order("2", BigDecimal.TWO);
				orderRepository.save(order2);
				// commit
				return null;
			});
		} catch (DataIntegrityViolationException e) {
			System.out.println("주문번호 중복 복구 작업");
		}

	}
}
