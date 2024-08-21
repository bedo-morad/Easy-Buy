package com.easybuy.easybuy.kafka.order;

public record Customer(
        String id,
        String firstName,
        String lastName,
        String email
) {
}
