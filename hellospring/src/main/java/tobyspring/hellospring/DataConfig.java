package tobyspring.hellospring;

import java.time.Clock;

import javax.sql.DataSource;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.client.RestTemplate;

import jakarta.persistence.EntityManagerFactory;
import tobyspring.hellospring.api.ApiTemplate;
import tobyspring.hellospring.api.ErApiExtractor;
import tobyspring.hellospring.api.SimpleApiExecutor;
import tobyspring.hellospring.data.OrderRepository;
import tobyspring.hellospring.exrate.CachedExRateProvider;
import tobyspring.hellospring.exrate.RestTemplateExRateProvider;
import tobyspring.hellospring.payment.ExRateProvider;
import tobyspring.hellospring.payment.PaymentService;

/**
 * 오브젝트 만들기 + 의존관계 설정하기
 */
@Configuration
public class DataConfig {

	// data source
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
	}
	// entity manager factory

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		// FactoryBean -> 클래스 안의 다른 빈 오브젝트 만드는 코드 가짐
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource());
		emf.setPackagesToScan("tobyspring.hellospring");
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter(){{
			setDatabase(Database.H2);
			setGenerateDdl(true);
			setShowSql(true);
		}});
		return emf;
	}

	@Bean
	public BeanPostProcessor persistenceAnnotationBeanPostProcessor() {
		return new PersistenceAnnotationBeanPostProcessor();
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	@Bean
	public OrderRepository orderRepository(){
		return new OrderRepository();
	}

}
