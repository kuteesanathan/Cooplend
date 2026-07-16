package com.tui.cooplend.dtos;

import java.math.BigDecimal;

public record LoanProductResponse(
        Long id,
        String code,
        String name,
        BigDecimal minimumAmount,
        BigDecimal maximumAmount,
        BigDecimal annualInterestRate,
        Integer minimumTermMonths,
        Integer maximumTermMonths,
        boolean active
) {
}
