package com.bakirwebservice.paymentservice.api.response;

import com.bakirwebservice.paymentservice.model.dto.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor

public class QueryTrackingNumberResponse {

    List<OrderStatus> orderStatuses;

}
