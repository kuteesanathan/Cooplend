package com.tui.cooplend.services;

import com.tui.cooplend.commonerrors.BusinessRuleViolationException;
import com.tui.cooplend.commonerrors.ResourceNotFoundException;
import com.tui.cooplend.dtos.LoanApplicationRequest;
import com.tui.cooplend.dtos.LoanApplicationResponse;
import com.tui.cooplend.entities.LoanApplication;
import com.tui.cooplend.entities.LoanProduct;
import com.tui.cooplend.entities.Member;
import com.tui.cooplend.entities.User;
import com.tui.cooplend.enums.AssessmentResult;
import com.tui.cooplend.enums.LoanApplicationStatus;
import com.tui.cooplend.enums.LoanStatus;
import com.tui.cooplend.mappers.LoanApplicationMapper;
import com.tui.cooplend.repositories.LoanApplicationRepository;
import com.tui.cooplend.repositories.LoanProductRepository;
import com.tui.cooplend.repositories.LoanRepository;
import com.tui.cooplend.repositories.MemberRepository;
import com.tui.cooplend.rules.AssessmentOutcome;
import com.tui.cooplend.rules.EligibilityAssessmentService;
import com.tui.cooplend.rules.EligibilityContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Getter
@Setter
public class LoanApplicationService {
    private final LoanApplicationRepository loanApplicationRepository;
    private final MemberRepository memberRepository;
    private final LoanProductRepository loanProductRepository;
    private final LoanRepository loanRepository;
    private final LoanApplicationMapper loanApplicationMapper;
    private final EligibilityAssessmentService eligibilityAssessmentService;
    private final AuditEntryService auditEntryService;

    @Transactional
    public LoanApplicationResponse submit(LoanApplicationRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member " + request.memberId() + " not found"));
        LoanProduct product = loanProductRepository.findById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Loan product " + request.productId() + " not found"));
        if (!member.isActive()) {
            throw new BusinessRuleViolationException("MEMBER_NOT_ACTIVE", "A suspended member cannot submit a loan application");
        }
        if (!product.isActive()) {
            throw new BusinessRuleViolationException("PRODUCT_NOT_ACTIVE", "This loan product is not active");
        }
        if (loanApplicationRepository.existsByMemberAndProductAndStatus(member, product, LoanApplicationStatus.PENDING)) {
            throw new BusinessRuleViolationException("DUPLICATE_PENDING_APPLICATION",
                    "Member already has a pending application for this product");
        }

        LoanApplication application = LoanApplication.builder()
                .memberId(member)
                .productId(product)
                .amount(request.amount())
                .termMonths(request.termMonths())
                .purpose(request.purpose())
                .status(LoanApplicationStatus.PENDING)
                .submittedDate(LocalDateTime.now())
                .disbursed(false)
                .build();
        loanApplicationRepository.save(application);

        boolean hasActiveUnpaidLoan = !loanRepository
                .findByApplicationIdMemberIdAndStatus(member.getId(), LoanStatus.ACTIVE).isEmpty();
        EligibilityContext context = EligibilityContext.from(
                member, product, request.amount(), request.termMonths(),
                false, hasActiveUnpaidLoan
        );
        AssessmentOutcome outcome = eligibilityAssessmentService.assess(context);
        application.setAssessmentResult(AssessmentResult.valueOf((outcome.eligible() ? "ELIGIBLE: " : "NOT ELIGIBLE: ") + outcome.summarise()));

        auditEntryService.record("APPLICATION_SUBMITTED", "LoanApplication", application.getId(),
                "Submitted by member " + member.getMemberNumber() + " for product " + product.getCode());

        return loanApplicationMapper.toResponse(application);
    }

    public LoanApplicationResponse getById(Long id) {
        return loanApplicationMapper.toResponse(findOrThrow(id));
    }

    public Page<LoanApplicationResponse> list(Pageable pageable) {
        return loanApplicationRepository.findAllWithMemberAndProduct(pageable).map(loanApplicationMapper::toResponse);
    }

    @Transactional
    public LoanApplicationResponse approve(Long id, User reviewer) {
        LoanApplication application = findOrThrow(id);
        application.approve(reviewer, application.getAssessmentResult());
        auditEntryService.record("APPLICATION_APPROVED", "LoanApplication", id, "Approved by " + reviewer.getEmail());
        return loanApplicationMapper.toResponse(application);
    }

    @Transactional
    public LoanApplicationResponse reject(Long id, User reviewer, String reason) {
        LoanApplication application = findOrThrow(id);
        application.reject(reviewer, reason, application.getAssessmentResult());
        auditEntryService.record("APPLICATION_REJECTED", "LoanApplication", id,
                "Rejected by " + reviewer.getEmail() + ": " + reason);
        return loanApplicationMapper.toResponse(application);
    }

    LoanApplication findOrThrow(Long id) {
        return loanApplicationRepository.findWithDetailsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan application " + id + " not found"));

    }
}