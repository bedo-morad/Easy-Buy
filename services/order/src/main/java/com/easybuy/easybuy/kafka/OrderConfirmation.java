package com.easybuy.easybuy.kafka;

import com.easybuy.easybuy.customer.CustomerResponse;
import com.easybuy.easybuy.order.PaymentMethod;
import com.easybuy.easybuy.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {

}
