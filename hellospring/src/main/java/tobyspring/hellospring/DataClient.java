package tobyspring.hellospring;

import java.math.BigDecimal;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import tobyspring.hellospring.data.OrderRepository;
import tobyspring.hellospring.order.Order;

public class DataClient {
	public static void main(String[] args) {
		BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
		OrderRepository orderRepository = beanFactory.getBean(OrderRepository.class);
		Order order = new Order("2",BigDecimal.TEN);
		orderRepository.save(order);

	}
}
