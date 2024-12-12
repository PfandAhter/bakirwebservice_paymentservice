package com.bakirwebservice.paymentservice.rest.service.payment.strategy;

import com.bakirwebservice.paymentservice.api.client.BakirBankServiceClient;
import com.bakirwebservice.paymentservice.api.request.payment.CancelPaymentRequest;
import com.bakirwebservice.paymentservice.api.request.payment.MakePaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class BakirBankPayment implements PaymentStrategy {

    private final BakirBankServiceClient bakirBankServiceClient;

    private final String customerId;

    private final String accountName;

    @Override
    public Boolean pay(double amount) {
        MakePaymentRequest makePaymentRequest = new MakePaymentRequest();
        makePaymentRequest.setAmount(amount);
        makePaymentRequest.setDescription("BAKIRWEBSERVICE ODEMESI");
        makePaymentRequest.setFromId(customerId);
        makePaymentRequest.setFromAccountName(accountName);
        makePaymentRequest.setToAccountName("BAKIRWEBSERVICE");
        makePaymentRequest.setFromId("BAKIRWEBSERVICE");

        Boolean isSuccessfully = bakirBankServiceClient.makePayment(makePaymentRequest);

        log.info("Payment made via Bakir Bank");
        return isSuccessfully;
    }

    @Override
    public Boolean cancel(double amount) {
        CancelPaymentRequest cancelPaymentRequest = new CancelPaymentRequest();
        cancelPaymentRequest.setAmount(amount);
        cancelPaymentRequest.setDescription("BAKIRWEBSERVICE PARA IADESI");
        cancelPaymentRequest.setFromId(customerId);
        cancelPaymentRequest.setFromAccountName(accountName);
        cancelPaymentRequest.setToAccountName("BAKIRWEBSERVICE");
        cancelPaymentRequest.setToId("BAKIRWEBSERVICE");

        Boolean isCancelled = bakirBankServiceClient.cancelPayment(cancelPaymentRequest);

        log.info("Payment canceled via Bakir Bank");
        return isCancelled;
    }
}
