package com.bakirwebservice.paymentservice.api.client;

import com.bakirwebservice.paymentservice.api.request.payment.CancelPaymentRequest;
import com.bakirwebservice.paymentservice.api.request.payment.MakePaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "BakirBank" , url = "${client.feign.bakirBank.path}")
public interface BakirBankServiceClient {

    @GetMapping("${client.feign.bakirBank.makePayment}")
    Boolean makePayment(MakePaymentRequest makePaymentRequest);

    @GetMapping("${client.feign.bakirBank.cancelPayment}")
    Boolean cancelPayment(CancelPaymentRequest cancelPaymentRequest);


}
