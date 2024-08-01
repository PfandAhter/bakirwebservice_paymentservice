package com.bakirwebservice.paymentservice.rest.service.interfaces;

import com.bakirwebservice.paymentservice.model.CommonHeader;
import jakarta.servlet.http.HttpServletRequest;

public interface IHeaderService {

    CommonHeader getHeader(HttpServletRequest request);
}
