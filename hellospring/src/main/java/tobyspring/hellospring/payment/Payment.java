package tobyspring.hellospring.payment;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

public class Payment {

	// 주문번호
	// 외국 통화 종류
	// 외국 통화 기준 결제 금액
	private final Long orderId;
	private final String currency;
	private final BigDecimal foreignCurrencyAmount;
	// 적용 환율
	private final BigDecimal exRate;
	private final BigDecimal convertedAmount;
	private final LocalDateTime validUntil;

	public Payment(Long orderId, String currency, BigDecimal foreignCurrencyAmount, BigDecimal exRate,
		BigDecimal convertedAmount, LocalDateTime validUntil) {
		this.orderId = orderId;
		this.currency = currency;
		this.foreignCurrencyAmount = foreignCurrencyAmount;
		this.exRate = exRate;
		this.convertedAmount = convertedAmount;
		this.validUntil = validUntil;
	}

	public static Payment createPrepared(Long orderId, String currency, BigDecimal foreignCurrencyAmount,
		BigDecimal exRate, LocalDateTime now) {
		BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate); // 계산하는 로직
		LocalDateTime validUntil = now.plusMinutes(30);

		return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
	}

	public boolean isValid(Clock clock) {
		return LocalDateTime.now(clock).isBefore(this.validUntil);
	}

	public Long getOrderId() {
		return orderId;
	}

	public String getCurrency() {
		return currency;
	}

	public BigDecimal getForeignCurrencyAmount() {
		return foreignCurrencyAmount;
	}

	public BigDecimal getExRate() {
		return exRate;
	}

	public BigDecimal getConvertedAmount() {
		return convertedAmount;
	}

	public LocalDateTime getValidUntil() {
		return validUntil;
	}

	@Override
	public String toString() {
		return "Payment{" +
			"orderId=" + orderId +
			", currency='" + currency + '\'' +
			", foreignCurrencyAmount=" + foreignCurrencyAmount +
			", exRate=" + exRate +
			", convertedAmount=" + convertedAmount +
			", validUntil=" + validUntil +
			'}';
	}
}
