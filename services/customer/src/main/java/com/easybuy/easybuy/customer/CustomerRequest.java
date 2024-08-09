package com.easybuy.easybuy.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message = "first name is required")
        String firstname,
        @NotNull(message = "last name is required")
        String lastname,
        @NotNull(message = "email is required")
        @Email(message =  "email is not valid")
        String email,
        Address address
) {
}
