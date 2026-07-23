package com.tui.cooplend.dtos;

import com.tui.cooplend.enums.AssessmentResult;
import com.tui.cooplend.enums.LoanApplicationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LoanApplicationResponse(
        Long id,
        Long memberId,
        String fullname,
        Long productId,
        String productName,
        BigDecimal amount,
        Integer termMonths,
        String purpose,
        LoanApplicationStatus status,
        AssessmentResult assessmentResult,
        String reviewReason,
        LocalDateTime submittedDate,
        LocalDateTime reviewedDate,
        Long reviewerId,
        boolean disbursed
) {
}
