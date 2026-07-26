package com.tui.cooplend.dtos;

import com.tui.cooplend.enums.PaymentRequestStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MockMobileMoneyGateway implements PaymentGateway{
    @Override
    public PaymentRequestResponse initiate(PaymentRequestCreateRequest request){
        String providerReference = "MOCKPROV-" + UUID.randomUUID().toString().substring(0,12).toUpperCase();
        return new PaymentRequestResponse(providerReference, PaymentRequestStatus.PENDING, "Payment request accepted by mock provider, awaiting webhook confirmation");
    }
}
