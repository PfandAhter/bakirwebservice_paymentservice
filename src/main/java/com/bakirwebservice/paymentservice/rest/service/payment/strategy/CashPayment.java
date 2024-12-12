package com.bakirwebservice.paymentservice.rest.service.payment.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CashPayment implements PaymentStrategy{

    private final String name;

    @Override
    public Boolean pay(double amount) {
        log.info("Odeme yapiliyor. Odeme tipi: CASH, kisi ismi: " + name + " miktar: " + amount);
        return true;
    }

    @Override
    public Boolean cancel(double amount) {
        return true;
    }
}
