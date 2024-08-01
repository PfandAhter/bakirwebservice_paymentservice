package com.bakirwebservice.paymentservice.rest.service;

import com.bakirwebservice.paymentservice.api.response.BuyOrdersOnCartTrackingNumberResponse;
import com.bakirwebservice.paymentservice.model.entity.OrderList;
import com.bakirwebservice.paymentservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j

public class OrderListServiceImpl {

    private final OrderRepository orderRepository;

    public BuyOrdersOnCartTrackingNumberResponse buyItemsInCartResponseTrackingNumber (String localUsername, String productCode , int orderQuantity , String trackingNumber , Long cost){
        OrderList orderList = new OrderList();

        orderList.setActive(1);
        orderList.setTrackingNumber(trackingNumber);
        orderList.setCustomerName(localUsername);
        orderList.setProductCode(productCode);
        orderList.setStatus("PREPARING");
        orderList.setOrderQuantity(orderQuantity);

        orderRepository.save(orderList);

        return new BuyOrdersOnCartTrackingNumberResponse(cost,trackingNumber);
    }


}

