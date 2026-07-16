package com.tui.cooplend.dtos;

import com.tui.cooplend.entities.Loan;
import com.tui.cooplend.enums.PaymentSource;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record RepaymentRequest(
        Loan loanId,
        @Positive BigDecimal amount,
        String transactionReference,
        PaymentSource source,
        Long recordedById
) {
}
