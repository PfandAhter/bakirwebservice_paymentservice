package com.bakirwebservice.paymentservice.rest.service.interfaces;

import com.bakirwebservice.paymentservice.api.request.*;
import com.bakirwebservice.paymentservice.api.response.BaseResponse;
import com.bakirwebservice.paymentservice.api.response.GetItemsListInCartResponse;
import com.bakirwebservice.paymentservice.exceptions.ShoppingCartEmptyException;

public interface IShoppingCartService {
    BaseResponse deleteFromCart(DeleteFromCartRequest request);

    BaseResponse addToCart(AddToCartRequest request);

    GetItemsListInCartResponse getShoppingCart(BaseRequest request)throws ShoppingCartEmptyException;

    void startShoppingCartCheckout(ShoppingCartCheckoutRequest request);

}
