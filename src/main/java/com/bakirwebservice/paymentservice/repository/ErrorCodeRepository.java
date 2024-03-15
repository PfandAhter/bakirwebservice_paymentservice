package com.bakirwebservice.paymentservice.repository;

import com.bakirwebservice.paymentservice.model.entity.ErrorCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorCodeRepository extends JpaRepository<ErrorCodes,Long> {

    ErrorCodes findErrorByError(String errorCode);

}
