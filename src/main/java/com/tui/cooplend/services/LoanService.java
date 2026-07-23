package com.tui.cooplend.services;

import com.tui.cooplend.commonerrors.BusinessRuleViolationException;
import com.tui.cooplend.commonerrors.ResourceNotFoundException;
import com.tui.cooplend.dtos.LoanResponse;
import com.tui.cooplend.entities.Loan;
import com.tui.cooplend.entities.LoanApplication;
import com.tui.cooplend.enums.LoanApplicationStatus;
import com.tui.cooplend.enums.LoanStatus;
import com.tui.cooplend.mappers.LoanMapper;
import com.tui.cooplend.repositories.LoanApplicationRepository;
import com.tui.cooplend.repositories.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanMapper loanMapper;
    private final AuditEntryService auditEntryService;

    @Transactional
    public LoanResponse disburse(Long applicationId) {
        LoanApplication application = loanApplicationRepository.findWithDetailsById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan application " + applicationId + " not found"));

        if (application.getStatus() != LoanApplicationStatus.APPROVED) {
            throw new BusinessRuleViolationException("APPLICATION_NOT_APPROVED", "Only an approved application may be disbursed");
        }
        if (loanRepository.existsByApplicationId(applicationId)) {
            throw new BusinessRuleViolationException("LOAN_ALREADY_EXISTS", "This application has already created a loan");
        }
        BigDecimal principal = application.getAmount();
        BigDecimal annualRate = application.getProduct().getAnnualInterestRate();
        BigDecimal termMonths = BigDecimal.valueOf(application.getTermMonths());
        BigDecimal interest = principal.multiply(annualRate).multiply(termMonths);

        BigDecimal totalDue = principal.add(interest);

        Loan loan = Loan.builder()
                .accountNumber(generateUniqueAccountNumber())
                .application(application)
                .principal(principal)
                .interest(interest)
                .totalDue(totalDue)
                .outstandingBalance(totalDue)
                .disbursedAt(LocalDateTime.now())
                .status(LoanStatus.ACTIVE)
                .build();
        loanRepository.save(loan);

        application.markDisbursed();

        auditEntryService.record("LOAN_DISBURSED", "Loan", loan.getId(),
                "Disbursed " + totalDue + " (principal " + principal + " + interest " + interest
                        + ") from application " + applicationId);

        return loanMapper.toResponse(loan);
    }

    public LoanResponse getById(Long id) {
        return loanMapper.toResponse(findOrThrow(id));
    }
}
