package com.tui.cooplend.dtos;

import com.tui.cooplend.enums.AssessmentResult;
import com.tui.cooplend.enums.LoanApplicationStatus;
import jakarta.validation.constraints.NotNull;

//Used by a reviewer/loan officer to approve, reject, or otherwise assess an application
public record LoanApplicationReviewRequest(
        @NotNull LoanApplicationStatus status,
        @NotNull AssessmentResult assessmentResult,
        String reviewReason,
        @NotNull Long reviewerId
) {
}
