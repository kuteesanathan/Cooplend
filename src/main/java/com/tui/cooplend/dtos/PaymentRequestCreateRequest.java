package com.tui.cooplend.dtos;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaymentRequestCreateRequest(
        Long loanId,
        @Positive BigDecimal amount,
        String internalReference
) {
}
