package com.bakirwebservice.paymentservice.api.request;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter

public class MicroServiceStoppedRequest {

    private String serviceCode;

    private String serviceName;

    private String errorCode;

    private Timestamp serviceStoppedDate;

    private String serviceStatus;
}
