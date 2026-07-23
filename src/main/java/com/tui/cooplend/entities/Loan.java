package com.tui.cooplend.entities;

import com.tui.cooplend.commonerrors.BusinessRuleViolationException;
import com.tui.cooplend.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private LoanApplication applicationId;

    @Column(name = "principal")
    private BigDecimal principal;

    @Column(name = "interest")
    private BigDecimal interest;

    @Column(name = "total_due")
    private BigDecimal totalDue;

    @Column(name = "outstanding_balance")
    private BigDecimal outstandingBalance;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private LoanStatus status = LoanStatus.ACTIVE;

    public void applyRepayment(BigDecimal amount){
        if (status == LoanStatus.CLOSED){
            throw new BusinessRuleViolationException("LOAN_CLOSED", "This loan is already closed and cannot accept further repayments");
        }
        if (amount == null || amount.signum() <= 0){
            throw new BusinessRuleViolationException("INVALID_REPAYMENT_AMOUNT", "Repayment amount must be positive");
        }
        if (amount.compareTo(outstandingBalance) > 0){
            throw new BusinessRuleViolationException("REPAYMENT_EXCEEDS_BALANCE", "Repayment amount exceeds the outstanding balance");
        }
        this.outstandingBalance = this.outstandingBalance.subtract(amount);
        if (this.outstandingBalance.signum() == 0){
            close();
        }
    }

    public void close(){
        this.status = LoanStatus.CLOSED;
        this.endDate = LocalDate.from(LocalDateTime.now());
    }

    public boolean hasOverdueOrUnpaidBalance(){
        return this.status == LoanStatus.ACTIVE && this.outstandingBalance.signum() > 0;
    }
}
