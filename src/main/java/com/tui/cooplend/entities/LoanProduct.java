package com.tui.cooplend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "loan_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(name = "minimum_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal minimumAmount;

    @Column(name = "maximum_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal maximumAmount;

    @Column(name = "annual_interest_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal annualInterestRate;

    @Column(name = "minimum_term_months", nullable = false)
    private Integer minimumTermMonths;

    @Column(name = "maximum_term_months", nullable = false)
    private Integer maximumTermMonths;

    @Column(name = "active", nullable = false)
    @Builder.Default
    private boolean active = true;

    public void activate(){
        this.active = true;
    }
    public void deactivate(){
        this.active = false;
    }

    public boolean amountWithinLimits(BigDecimal amount){
        return amount.compareTo(minimumAmount) >= 0 && amount.compareTo(maximumAmount) <= 0;
    }

    public boolean termWithinLimits(int termMonths){
        return termMonths >= minimumTermMonths && termMonths <= maximumTermMonths;    }
}