package tobyspring.hellospring.order;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

public class Order {
	private Long id;
	private String no;
	private BigDecimal total;

	// INSERT SELECT
	public Order(){}
	public Order(String no, BigDecimal total){
		this.no = no;
		this.total = total;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Order{" +
			"id=" + id +
			", no='" + no + '\'' +
			", total=" + total +
			'}';
	}
}
