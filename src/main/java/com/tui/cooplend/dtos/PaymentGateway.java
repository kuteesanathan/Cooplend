package com.tui.cooplend.dtos;

public interface PaymentGateway {
    PaymentRequestResponse initiate(PaymentRequestCreateRequest request);
}
