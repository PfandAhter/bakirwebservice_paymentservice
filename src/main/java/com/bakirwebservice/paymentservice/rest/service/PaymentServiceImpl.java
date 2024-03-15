package com.bakirwebservice.paymentservice.rest.service;

import com.bakirwebservice.paymentservice.api.request.*;
import com.bakirwebservice.paymentservice.api.response.BaseResponse;
import com.bakirwebservice.paymentservice.api.response.BuyOrdersOnCartTrackingNumberResponse;
import com.bakirwebservice.paymentservice.api.response.GetBalanceResponse;
import com.bakirwebservice.paymentservice.api.response.GetProductDetailsResponse;
import com.bakirwebservice.paymentservice.exceptions.BalanceNotEnoughException;
import com.bakirwebservice.paymentservice.model.dto.OrderListDTO;
import com.bakirwebservice.paymentservice.model.entity.Balance;
import com.bakirwebservice.paymentservice.model.entity.OrderList;
import com.bakirwebservice.paymentservice.repository.BalanceRepository;
import com.bakirwebservice.paymentservice.repository.OrderRepository;
import com.bakirwebservice.paymentservice.rest.service.interfaces.IPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.bakirwebservice.paymentservice.lib.constants.PropertyConstants.REQUEST_RESTTEMPLATE_MICROSERVICE_PRODUCT_SERVICE_PRODUCT_GET_PRODUCT_INFO;
import static com.bakirwebservice.paymentservice.lib.constants.PropertyConstants.REQUEST_RESTTEMPLATE_MICROSERVICE_TOKEN_SERVICE_EXTRACT_USERNAME;

@Service
@RequiredArgsConstructor
@Slf4j

public class PaymentServiceImpl implements IPaymentService {

    private final OrderRepository orderRepository;

    private final BalanceRepository balanceRepository;

    private final RestTemplate restTemplate;

    private final MapperServiceImpl mapperService;

    @Value(REQUEST_RESTTEMPLATE_MICROSERVICE_TOKEN_SERVICE_EXTRACT_USERNAME)
    private String extractUsernamePath;

    @Override
    public BaseResponse addBalance(AddBalanceRequest request) {
        String extractedUsername = restTemplate.postForObject(extractUsernamePath, request, String.class);

        Balance localUserBalance = balanceRepository.findBalanceByUsername(extractedUsername);
        localUserBalance.setAmount(localUserBalance.getAmount() + request.getAmount());
        log.info("Balance added username : " + localUserBalance.getUsername() + " , balance amount : " + localUserBalance.getAmount());
        balanceRepository.save(localUserBalance);
        return new BaseResponse();

    }

    @Override
    public BaseResponse createBalance(CreateBalanceRequest request) {

        Balance newBalance = new Balance();
        newBalance.setMoney_code("TL");
        newBalance.setAmount(0L);
        newBalance.setUser_id(request.getUser_id());
        newBalance.setUsername(request.getUsername());
        balanceRepository.save(newBalance);

        return new BaseResponse();

    }

    @Override
    public GetBalanceResponse getBalance(BaseRequest request) {
        String localUsername = restTemplate.postForObject(extractUsernamePath, request, String.class);
        Balance localUserBalance = balanceRepository.findBalanceByUsername(localUsername);

        GetBalanceResponse getBalanceResponse = new GetBalanceResponse();
        getBalanceResponse.setBalance(localUserBalance.getAmount());

        return getBalanceResponse;
    }

    @Override
    public BaseResponse deleteOrder(DeleteOrderRequest request) {
        String localUsername = restTemplate.postForObject(extractUsernamePath, request, String.class);
        if (orderRepository.findByCustomerNameAndProductCode(localUsername, request.getProductCode()).getOrderQuantity() <= request.getQuantity()) {
            orderRepository.delete(orderRepository.findByCustomerNameAndProductCode(localUsername, request.getProductCode()));
        } else {
            OrderList localOrderList = orderRepository.findByCustomerNameAndProductCode(localUsername, request.getProductCode());
            localOrderList.setOrderQuantity(localOrderList.getOrderQuantity() - request.getQuantity());
            orderRepository.save(localOrderList);
        }
        return new BaseResponse();
    }

    @Override
    public BaseResponse createOrder(CreateOrderRequest request) {
        String localUsername = restTemplate.postForObject(extractUsernamePath, request, String.class);

        if (orderRepository.findByCustomerNameAndProductCode(localUsername, request.getProductCode()) != null) {
            OrderList localOrderList = orderRepository.findByCustomerNameAndProductCode(localUsername, request.getProductCode());
            localOrderList.setOrderQuantity(request.getOrderQuantity()+localOrderList.getOrderQuantity());
            orderRepository.save(localOrderList);
        } else {
            orderRepository.save(OrderList.builder()
                    .orderQuantity(request.getOrderQuantity())
                    .active(1)
                    .customerName(localUsername)
                    .productCode(request.getProductCode()).build());
        }
        return new BaseResponse();
    }

    @Value(REQUEST_RESTTEMPLATE_MICROSERVICE_PRODUCT_SERVICE_PRODUCT_GET_PRODUCT_INFO)
    private String getProductInfoFromProductServicePath;

    @Override
    public List<GetProductDetailsResponse> getOrderList(BaseRequest request) {
        String localUsername = restTemplate.postForObject(extractUsernamePath, request, String.class);

        List<OrderListDTO> localOrderList = mapperService.modelMapper(orderRepository.findOrderListsByCustomerNameAndActive(localUsername, 1), OrderListDTO.class);
        ArrayList<GetProductDetailsResponse> detailsResponse = restTemplate.postForObject(getProductInfoFromProductServicePath, localOrderList, ArrayList.class);

        return detailsResponse;
    }

    public BuyOrdersOnCartTrackingNumberResponse buyOrdersOnCartsAndReturnTrackingNumber (BaseRequest request) throws BalanceNotEnoughException{
        String localUsername = restTemplate.postForObject(extractUsernamePath, request, String.class);
        Long localUserBalance = balanceRepository.findBalanceByUsername(localUsername).getAmount();

        Long localAmount = 0L;

        List<GetProductDetailsResponse> localOrderListResponse = getOrderList(request);

        Long amount = localOrderListResponse.get(0).getPrice();

        for(int i = 0 ; i < localOrderListResponse.size();i++){
            GetProductDetailsResponse localResponse = localOrderListResponse.get(i);
            localAmount += (localOrderListResponse.get(i).getPrice())*(localOrderListResponse.get(i).getOrderQuantity());
        }

        if(localUserBalance >= localAmount){
            List<OrderList> localOrderLists = orderRepository.findOrderListsByCustomerNameAndActive(localUsername,1);
            for(int i = 0 ; i<localOrderLists.size();i++){
                localOrderLists.get(i).setActive(2);
                localOrderLists.get(i).setStatus("PREPARING");
                orderRepository.save(localOrderLists.get(i));
            }
            return BuyOrdersOnCartTrackingNumberResponse.builder()
                    .cost(localAmount)
                    .trackingNumber(balanceRepository.findBalanceByUsername(localUsername).getId()).build();
        }else{
            throw new BalanceNotEnoughException("Not enough balance");
        }


    }

}
