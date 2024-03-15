package com.bakirwebservice.paymentservice.rest.aspect;


import com.bakirwebservice.paymentservice.model.entity.ErrorCodes;
import com.bakirwebservice.paymentservice.repository.ErrorCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class GeneralInterceptorAspect {

    private final ErrorCodeRepository errorCodeRepository;

    @Before(value = "execution(* com.bakirwebservice.paymentservice.exceptions.GlobalExceptionHandler.*(..)) ")
    public void beforeException (JoinPoint joinPoint){
        Object [] parameters = joinPoint.getArgs();
        for(Object param : parameters){
            if(param instanceof Exception){

                if(errorCodeRepository.findErrorByError(param.getClass().getName()) == null){
                    ErrorCodes errorCodes = new ErrorCodes();
                    errorCodes.setError(param.getClass().getName());
                    errorCodes.setError_description(((Exception) param).getMessage());
                    errorCodeRepository.save(errorCodes);
                    Long localCode = 3000L;
                    errorCodes.setError_code(errorCodes.getId()+localCode);
                    errorCodeRepository.save(errorCodes);
                }
            }
        }
    }
}