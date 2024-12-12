package com.bakirwebservice.paymentservice.api.request;

import com.bakirwebservice.paymentservice.model.entity.ShoppingCart;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class ShoppingCartCheckOutSteps extends ShoppingCartCheckoutRequest{

    List<ShoppingCart> shoppingCartList;
}
