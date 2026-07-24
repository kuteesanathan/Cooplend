package com.tui.cooplend.services;

import com.tui.cooplend.commonerrors.DuplicateResourceException;
import com.tui.cooplend.commonerrors.ResourceNotFoundException;
import com.tui.cooplend.dtos.RepaymentResponse;
import com.tui.cooplend.entities.Loan;
import com.tui.cooplend.entities.Repayment;
import com.tui.cooplend.entities.User;
import com.tui.cooplend.enums.PaymentSource;
import com.tui.cooplend.repositories.LoanRepository;
import com.tui.cooplend.repositories.RepaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RepaymentService {
    private final RepaymentRepository repaymentRepository;
    private final LoanRepository loanRepository;
    private final AuditEntryService auditService;

    @Transactional
    public RepaymentResponse record(Long loanId,
                                    BigDecimal amount,
                                    String transactionReference,
                                    PaymentSource source,
                                    User recordedBy) {
        if (repaymentRepository.existsByTransactionReference(transactionReference)) {
            throw new DuplicateResourceException("Transaction reference " + transactionReference + " has already been used");
        }
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan " + loanId + " not found"));

        loan.applyRepayment(amount); // validates positive / <= balance / not closed, auto-closes at zero

        Repayment repayment = Repayment.builder()
                .loanId(loan)
                .amount(amount)
                .transactionReference(transactionReference)
                .source(source)
                .repaidAt(LocalDateTime.now())
                .recordedBy(recordedBy)
                .build();
        repaymentRepository.save(repayment);

        auditService.record("REPAYMENT_RECORDED", "Loan", loanId,
                "Repayment of " + amount + " via " + source + " (ref " + transactionReference + "), balance now " + loan.getOutstandingBalance());

        return toResponse(repayment);
    }

    public Page<RepaymentResponse> listForLoan(Loan loanId, Pageable pageable) {
        return repaymentRepository.findByLoanId(loanId, pageable).map(this::toResponse);
    }

    private RepaymentResponse toResponse(Repayment repayment) {
        return new RepaymentResponse(
                repayment.getId(),
                repayment.getLoanId(),
                repayment.getAmount(),
                repayment.getTransactionReference(),
                repayment.getSource(),
                repayment.getRecordedBy().getId(),
                repayment.getRepaidAt(),
                repayment.getLoanId().getOutstandingBalance()
        );
    }
}
