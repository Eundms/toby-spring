package tobyspring.hellospring;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentService {
    private final WebApiExRateProvider exRateProvider;

    public PaymentService() {
        this.exRateProvider  = new WebApiExRateProvider();
    }

    public Payment prepare (Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws Exception  {
        BigDecimal exRate = exRateProvider.getWebExRate(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate,convertedAmount, validUntil);
    }


}
