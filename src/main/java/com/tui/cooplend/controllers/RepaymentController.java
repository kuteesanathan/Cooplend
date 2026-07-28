package com.tui.cooplend.controllers;

import com.tui.cooplend.dtos.RepaymentRequest;
import com.tui.cooplend.dtos.RepaymentResponse;
import com.tui.cooplend.entities.Loan;
import com.tui.cooplend.entities.User;
import com.tui.cooplend.enums.PaymentSource;
import com.tui.cooplend.services.RepaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans/{loanId}/repayments")
@AllArgsConstructor
public class RepaymentController {
    private final RepaymentService repaymentService;

    @PostMapping
    public ResponseEntity<RepaymentResponse> record(@PathVariable Long loanId,
                                                    @Valid @RequestBody RepaymentRequest request
                                                   ) {
        RepaymentResponse response = repaymentService.record(
                loanId, request.amount(), request.transactionReference(), PaymentSource.CASH);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<RepaymentResponse>> list(@PathVariable Loan loanId, Pageable pageable) {
        return ResponseEntity.ok(repaymentService.listForLoan(loanId, pageable));
    }
}
