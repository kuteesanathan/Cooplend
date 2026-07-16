package com.tui.cooplend.dtos;

import com.tui.cooplend.enums.LoanStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanResponse(
        Long id,
        String accountNumber,
        Long applicationId,
        BigDecimal principal,
        BigDecimal interest,
        BigDecimal totalDue,
        BigDecimal outstandingBalance,
        LocalDate startDate,
        LocalDate endDate,
        LoanStatus status
) {
}
