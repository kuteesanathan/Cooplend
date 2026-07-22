package com.tui.cooplend.entities;

import com.tui.cooplend.commonerrors.InvalidStateTransitionException;
import com.tui.cooplend.enums.AssessmentResult;
import com.tui.cooplend.enums.LoanApplicationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private LoanProduct productId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "term_months", nullable = false)
    private Integer termMonths;

    @Column(nullable = false)
    private String purpose;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private LoanApplicationStatus status = LoanApplicationStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "assessment_result", nullable = false)
    @Builder.Default
    private AssessmentResult assessmentResult = AssessmentResult.NOT_ASSESSED;

    @Column(name = "review_reason")
    private String reviewReason;

    @Column(name = "submitted_date", nullable = false)
    private LocalDateTime submittedDate;

    @Column(name = "reviewed_date")
    private LocalDateTime reviewedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    private boolean disbursed;

    public Member getMember() {
        return memberId;
    }

    public LoanProduct getProduct() {
        return productId;
    }

    public void approve(User reviewer, AssessmentResult assessmentResult){
        requireStatus(LoanApplicationStatus.PENDING, "approve");
        this.status = LoanApplicationStatus.APPROVED;
        this.reviewer = reviewer;
        this.reviewedDate = LocalDateTime.now();
        this.assessmentResult = assessmentResult;
    }

    public void reject(User reviewer, String reviewReason, AssessmentResult assessmentResult){
        requireStatus(LoanApplicationStatus.PENDING, "reject");
        if (reviewReason == null || reviewReason.isBlank()){
            throw new IllegalArgumentException("A rejection reason is mandatory");
        }
        this.status = LoanApplicationStatus.REJECTED;
        this.reviewer = reviewer;
        this.reviewReason = reviewReason;
        this.reviewedDate = LocalDateTime.now();
        this.assessmentResult = assessmentResult;
    }

    public void markDisbursed(){
        if (this.status != LoanApplicationStatus.APPROVED){
            throw new InvalidStateTransitionException("Only an approved application may be disbursed");
        }
        if (this.disbursed){
            throw new InvalidStateTransitionException("This application has already created a loan");
        }
        this.disbursed = true;
    }

    private void requireStatus(LoanApplicationStatus required, String action){
        if (this.status != required){
            throw new InvalidStateTransitionException("Cannot " + action + " an application that is already " + this.status);
        }
    }
}
