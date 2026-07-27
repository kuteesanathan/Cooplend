package com.tui.cooplend.entities;

import com.tui.cooplend.enums.PaymentRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "internal_reference")
    private String internalReference;

    @Column(name = "provider_reference")
    private String providerReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private PaymentRequestStatus status = PaymentRequestStatus.PENDING;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public boolean isAlreadyFinal(){
        return status == PaymentRequestStatus.SUCCESS || status == PaymentRequestStatus.FAILED;
    }

    public void markSuccessful(){
        this.status = PaymentRequestStatus.SUCCESS;
        this.updatedDate = LocalDateTime.now();
    }

    public void markFailed(){
        this.status = PaymentRequestStatus.FAILED;
        this.updatedDate = LocalDateTime.now();
    }
}
