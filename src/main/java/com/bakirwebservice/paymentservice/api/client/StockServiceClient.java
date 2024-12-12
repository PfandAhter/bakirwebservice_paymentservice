package com.bakirwebservice.paymentservice.api.client;

import com.bakirwebservice.paymentservice.api.response.BaseResponse;
import com.bakirwebservice.paymentservice.api.response.ProductGetResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "StockService" , url = "${client.feign.stock-service.path}")
public interface StockServiceClient {

    @GetMapping("${client.feign.stock-service.getProduct}")
    ProductGetResponse getProductsDetail (@RequestParam("product") String productId);

    @PostMapping("${client.feign.stock-service.reduceStock}")
    BaseResponse reduceStock (@RequestParam("product") String productId, @RequestParam("quantity") int quantity);

    @GetMapping("${client.feign.stock-service.checkStock}")
    Boolean checkStock (@RequestParam("product") String productId, @RequestParam("quantity") int quantity);

}
