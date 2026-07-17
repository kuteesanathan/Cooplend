package com.tui.cooplend.dtos;

import java.math.BigDecimal;

public record LoanProductUpdateRequest(
        String name,
        BigDecimal minimumAmount,
        BigDecimal maximumAmount,
        BigDecimal annualInterestRate,
        Integer minimumTermMonths,
        Integer maximumTermMonths
) {
}
