package com.bakirwebservice.paymentservice.rest.aspect;

import com.bakirwebservice.paymentservice.api.client.MicroServiceRegisterClient;
import com.bakirwebservice.paymentservice.api.request.MicroServiceReadyRequest;
import com.bakirwebservice.paymentservice.api.request.MicroServiceStoppedRequest;
import com.bakirwebservice.paymentservice.rest.util.Util;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@Component
@Slf4j
@RequiredArgsConstructor
public class MicroServiceRegister {

    private final MicroServiceRegisterClient microServiceRegisterClient;

    private static final String microServiceCode = "PS0ea589c4b1a04e3ba448ebfee5f6f881";

    private static final String microServiceName = "PAYMENT-SERVICE";

    @EventListener(ApplicationReadyEvent.class)
    public void logToDataBaseServiceReady(){
       /* MicroServiceReadyRequest microServiceReadyRequest = new MicroServiceReadyRequest();
        microServiceReadyRequest.setServiceCode(microServiceCode);
        microServiceReadyRequest.setServiceStatus("UP");
        microServiceReadyRequest.setErrorCode("3000");
        microServiceReadyRequest.setServiceReadyDate(Timestamp.from(Instant.now()));
        microServiceReadyRequest.setServiceName(microServiceName);

        microServiceRegisterClient.microServiceReady(microServiceReadyRequest);*/
    }

    @PreDestroy
    public void testLogToDatabaseStopped(){
        /*MicroServiceStoppedRequest microServiceStoppedRequest = new MicroServiceStoppedRequest();
        microServiceStoppedRequest.setServiceStoppedDate(Timestamp.from(Instant.now()));
        microServiceStoppedRequest.setServiceName(microServiceName);
        microServiceStoppedRequest.setErrorCode("3000");
        microServiceStoppedRequest.setServiceStatus("DOWN");
        microServiceStoppedRequest.setServiceCode(microServiceCode);

        microServiceRegisterClient.microServiceStopped(microServiceStoppedRequest);*/
    }
}
