package com.tui.cooplend.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoanApplicationRejectRequest(
        @NotBlank String reason
) {
}
