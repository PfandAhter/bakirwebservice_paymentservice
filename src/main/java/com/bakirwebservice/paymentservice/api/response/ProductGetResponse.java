package com.bakirwebservice.paymentservice.api.response;

import com.bakirwebservice.paymentservice.model.dto.ProductDTO;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductGetResponse {
    private ProductDTO productDTO;
}
