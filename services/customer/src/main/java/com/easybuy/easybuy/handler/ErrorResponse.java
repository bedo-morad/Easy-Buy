package com.easybuy.easybuy.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
