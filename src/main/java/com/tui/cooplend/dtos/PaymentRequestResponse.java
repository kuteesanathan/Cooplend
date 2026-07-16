package com.tui.cooplend.dtos;

import com.tui.cooplend.enums.PaymentRequestStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentRequestResponse(
        Long id,
        String loanId,
        BigDecimal amount,
        String internalReference,
        String providerReference,
        PaymentRequestStatus status,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
