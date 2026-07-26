package com.tui.cooplend.dtos;

import com.tui.cooplend.enums.PaymentRequestStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentRequestResponse(
        String providerReference,
        PaymentRequestStatus status,
        String message
) {
}
