package com.easybuy.easybuy.order;

import java.math.BigDecimal;

public record OrderResponse(
        Integer Id,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId
) {
}
