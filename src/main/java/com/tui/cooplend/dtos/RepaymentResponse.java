package com.tui.cooplend.dtos;

import com.tui.cooplend.entities.Loan;
import com.tui.cooplend.enums.PaymentSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RepaymentResponse(
        Long id,
        Loan loanId,
        BigDecimal amount,
        String transactionReference,
        PaymentSource source,
        LocalDateTime date,
        Long recordedById
) {
}
