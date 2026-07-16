package com.tui.cooplend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record LoanProductRequest(
        @NotBlank String code,
        @NotBlank String name,
        @NotNull @Positive BigDecimal minimumAmount,
        @NotNull @Positive BigDecimal maximumAmount,
        @NotNull @Positive BigDecimal annualInterestRate,
        @NotNull @Positive Integer minimumTermMonths,
        @NotNull @Positive Integer maximumTermMonths,
        boolean active
) {
}
