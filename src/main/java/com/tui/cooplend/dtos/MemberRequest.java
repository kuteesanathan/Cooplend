package com.tui.cooplend.dtos;

import com.tui.cooplend.enums.MemberStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record MemberRequest(
        @NotBlank String memberNumber,
        @NotBlank String fullName,
        @NotBlank String nationalId,
        @NotBlank String telephone,
        @NotNull @Past LocalDate dateOfBirth,
        MemberStatus status
) {
}
