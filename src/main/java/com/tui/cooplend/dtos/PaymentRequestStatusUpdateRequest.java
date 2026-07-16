package com.tui.cooplend.dtos;

import com.tui.cooplend.enums.PaymentRequestStatus;

//Used when the payment provider calls back
public record PaymentRequestStatusUpdateRequest(
        PaymentRequestStatus status,
        String providerReference
) {
}
