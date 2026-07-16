package com.tui.cooplend.dtos;

import com.tui.cooplend.enums.MemberStatus;

import java.time.LocalDate;

public record MemberResponse(
        Long id,
        String memberNumber,
        String fullName,
        String nationalId,
        String telephone,
        LocalDate dateOfBirth,
        MemberStatus status
) {
}
