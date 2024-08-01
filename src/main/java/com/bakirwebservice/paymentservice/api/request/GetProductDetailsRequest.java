package com.bakirwebservice.paymentservice.api.request;

import com.bakirwebservice.paymentservice.model.dto.ShoppingCartDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetProductDetailsRequest {

    List<ShoppingCartDTO> cartDTOList;
}
