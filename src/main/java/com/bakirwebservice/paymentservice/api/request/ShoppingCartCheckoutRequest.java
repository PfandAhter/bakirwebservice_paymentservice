package com.bakirwebservice.paymentservice.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ShoppingCartCheckoutRequest extends BaseRequest{
    private String paymentType; // Ödeme yöntemi: "BAKIR_BANK", "CREDIT_CARD", vs.
    private int amount;         // Ödeme tutarı
    private Map<String, String> params; // Ödeme yöntemi parametreleri //
}