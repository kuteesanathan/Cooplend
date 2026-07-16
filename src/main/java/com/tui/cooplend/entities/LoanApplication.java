package com.tui.cooplend.entities;

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
}
