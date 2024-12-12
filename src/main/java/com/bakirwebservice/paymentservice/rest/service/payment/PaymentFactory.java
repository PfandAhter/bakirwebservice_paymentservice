package com.bakirwebservice.paymentservice.rest.service.payment;

import com.bakirwebservice.paymentservice.api.client.BakirBankServiceClient;
import com.bakirwebservice.paymentservice.rest.service.payment.strategy.BakirBankPayment;
import com.bakirwebservice.paymentservice.rest.service.payment.strategy.CashPayment;
import com.bakirwebservice.paymentservice.rest.service.payment.strategy.CreditCardPayment;
import com.bakirwebservice.paymentservice.rest.service.payment.strategy.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaymentFactory {

    private final BakirBankServiceClient bakirBankServiceClient;

    public PaymentStrategy createPayment(String paymentType, Map<String, String> params) {
        switch (paymentType) {
            case "BAKIR_BANK":
                return new BakirBankPayment(
                        bakirBankServiceClient,
                        params.get("customerId"),
                        params.get("accountName")
                );
            case "CREDIT_CARD":
                return new CreditCardPayment(params.get("cardNumber"));
            case "CASH":
                return new CashPayment(params.get("name"));
            default:
                throw new IllegalArgumentException("Invalid payment type: " + paymentType);
        }
    }
}
