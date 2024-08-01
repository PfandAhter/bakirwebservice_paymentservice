package com.bakirwebservice.paymentservice.rest.service.interfaces;

import com.bakirwebservice.paymentservice.api.request.*;
import com.bakirwebservice.paymentservice.api.response.BaseResponse;
import com.bakirwebservice.paymentservice.api.response.BuyOrdersOnCartTrackingNumberResponse;
import com.bakirwebservice.paymentservice.api.response.GetItemsListInCartResponse;
import com.bakirwebservice.paymentservice.exceptions.CartListEmptyException;
import com.bakirwebservice.paymentservice.exceptions.InsufficientBalanceException;

public interface IShoppingCartService {
    BaseResponse deleteItemOnCart(DeleteItemRequest request);

    BaseResponse addItemOnCart(AddItemRequest request);

    GetItemsListInCartResponse getItemListInCart(BaseRequest request)throws CartListEmptyException;

    BuyOrdersOnCartTrackingNumberResponse buyOrdersOnCartsAndReturnTrackingNumber(BaseRequest request)throws CartListEmptyException , InsufficientBalanceException;

}
