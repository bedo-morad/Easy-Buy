package com.easybuy.easybuy.product;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        Integer productId,
        String productName,
        String description,
        BigDecimal price,
        double quantity
) {
}
