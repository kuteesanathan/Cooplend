package com.tui.cooplend.rules;

import com.tui.cooplend.entities.LoanProduct;
import com.tui.cooplend.entities.Member;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public record EligibilityContext(
        Long memberId,
        boolean memberActive,
        Long productId,
        boolean productActive,
        BigDecimal requestedAmount,
        int requestedTermMonths,
        BigDecimal productMinAmount,
        BigDecimal productMaxAmount,
        int productMinTerm,
        int productMaxTerm,
        boolean hasPendingApplicationForSameProduct,
        boolean hasActiveLoanWithUnpaidBalance
) {
    public static EligibilityContext from(Member member, LoanProduct product, BigDecimal amount, int termMonths, boolean hasPendingApplicationForSameProduct, boolean hasActiveLoanWithUnpaidBalance){
        return new EligibilityContext(
                member.getId(),
                member.isActive(),
                product.getId(),
                product.isActive(),
                amount,
                termMonths,
                product.getMinimumAmount(),
                product.getMaximumAmount(),
                product.getMinimumTermMonths(),
                product.getMaximumTermMonths(),
                hasPendingApplicationForSameProduct,
                hasActiveLoanWithUnpaidBalance
        );
    }
}
