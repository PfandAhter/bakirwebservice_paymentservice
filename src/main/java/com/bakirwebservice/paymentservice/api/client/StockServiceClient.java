package com.bakirwebservice.paymentservice.api.client;

import com.bakirwebservice.paymentservice.api.request.ReduceStockRequest;
import com.bakirwebservice.paymentservice.api.response.BaseResponse;
import com.bakirwebservice.paymentservice.api.response.ProductGetResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "StockService" , url = "${client.feign.stock-service.path}")
public interface StockServiceClient {

    @GetMapping("${client.feign.stock-service.getProduct}")
    ProductGetResponse getProductsDetail (@RequestParam("product") String productId);

    @PostMapping("${client.feign.stock-service.reduceStock}")
    BaseResponse reduceStock (@RequestBody ReduceStockRequest reduceStockRequest);

}
