package com.tui.cooplend.entities;

import com.tui.cooplend.enums.PaymentSource;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "repayments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Repayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loanId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "transaction_reference", nullable = false, unique = true)
    private String transactionReference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentSource source;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recorded_by", nullable = false)
    private User recordedBy;
}

