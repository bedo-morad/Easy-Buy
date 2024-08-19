package com.easybuy.easybuy.payment;

import com.easybuy.easybuy.customer.CustomerResponse;
import com.easybuy.easybuy.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
