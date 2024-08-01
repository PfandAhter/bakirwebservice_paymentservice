package com.bakirwebservice.paymentservice.api.client;

import com.bakirwebservice.paymentservice.api.request.AddBalanceRequest;
import com.bakirwebservice.paymentservice.api.request.BaseRequest;
import com.bakirwebservice.paymentservice.api.response.BaseResponse;
import com.bakirwebservice.paymentservice.api.response.GetBalanceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "UserService" , url = "${client.feign.user-service.path}")
public interface UserServiceClient {

    @GetMapping("${client.feign.user-service.getBalance}")
    GetBalanceResponse getBalance (@RequestBody BaseRequest baseRequest);

    @PostMapping("${client.feign.user-service.addBalance}")
    BaseResponse addBalance (@RequestBody AddBalanceRequest addBalanceRequest);

}
