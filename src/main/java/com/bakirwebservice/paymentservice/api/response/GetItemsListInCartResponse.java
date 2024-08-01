package com.bakirwebservice.paymentservice.api.response;

import com.bakirwebservice.paymentservice.model.dto.ShoppingCartDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class GetItemsListInCartResponse {
    List<ShoppingCartDTO> shoppingCartList;
    private Long totalAmount;
}
