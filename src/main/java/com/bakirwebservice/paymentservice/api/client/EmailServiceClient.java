package com.bakirwebservice.paymentservice.api.client;

import com.bakirwebservice.paymentservice.api.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "EmailService" , url = "${client.feign.email-service.path}")
public interface EmailServiceClient {

    @PostMapping("${client.feign.token-service.extractUsername}")
    BaseResponse sendNotificationEmail(String sendNotificationRequest);

}
