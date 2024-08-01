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

    private String microServiceCode;

    private static final String microServiceName = "PAYMENT-SERVICE";

    @EventListener(ApplicationReadyEvent.class)
    public void logToDataBaseServiceReady(){
        microServiceCode = Util.generateCode();
        MicroServiceReadyRequest microServiceReadyRequest = new MicroServiceReadyRequest();
        microServiceReadyRequest.setMicroServiceCode(microServiceCode);
        microServiceReadyRequest.setMicroServiceStatus("UP");
        microServiceReadyRequest.setMicroServiceErrorCode("3000");
        microServiceReadyRequest.setMicroServiceReadyDate(Timestamp.from(Instant.now()));
        microServiceReadyRequest.setMicroServiceName(microServiceName);

        microServiceRegisterClient.microServiceReady(microServiceReadyRequest);
    }

    @PreDestroy
    public void testLogToDatabaseStopped(){
        MicroServiceStoppedRequest microServiceStoppedRequest = new MicroServiceStoppedRequest();
        microServiceStoppedRequest.setMicroServiceStoppedDate(Timestamp.from(Instant.now()));
        microServiceStoppedRequest.setMicroServiceName(microServiceName);
        microServiceStoppedRequest.setMicroServiceErrorCode("3000");
        microServiceStoppedRequest.setMicroServiceStatus("DOWN");
        microServiceStoppedRequest.setMicroServiceCode(microServiceCode);

        microServiceRegisterClient.microServiceStopped(microServiceStoppedRequest);
    }
}
