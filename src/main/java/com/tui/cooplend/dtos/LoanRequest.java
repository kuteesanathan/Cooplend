package com.tui.cooplend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

//Used to disburse a loan from ana approved application
public record LoanRequest(
        @NotNull Long applicationId,
        @NotBlank String accountNumber,
        @NotNull @Positive BigDecimal principal,
        @NotNull BigDecimal interest,
        @NotNull BigDecimal totalDue,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate
) {
}
