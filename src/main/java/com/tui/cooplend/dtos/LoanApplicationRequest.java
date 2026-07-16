package com.tui.cooplend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

//Used to submit a new a loan application
public record LoanApplicationRequest(
        @NotNull Long memberId,
        @NotNull Long productId,
        @NotNull @Positive BigDecimal amount,
        @NotNull @Positive Integer termMonths,
        @NotBlank String purpose
) {
}
