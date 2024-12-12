package com.bakirwebservice.paymentservice.rest.service.payment.strategy;

public class CreditCardPayment implements PaymentStrategy {

    private final String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public Boolean pay(double amount) {
        System.out.println("Payment completed via Credit Card. Card Number: " + cardNumber);
        return false;
    }

    @Override
    public Boolean cancel(double amount) {
        return true;
    }
}