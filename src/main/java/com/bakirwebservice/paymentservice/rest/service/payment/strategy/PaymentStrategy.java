package com.bakirwebservice.paymentservice.rest.service.payment.strategy;

public interface PaymentStrategy {
    Boolean pay(double amount);
    Boolean cancel(double amount);
}
