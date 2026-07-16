package com.tui.cooplend.repositories;

import com.tui.cooplend.entities.Loan;
import com.tui.cooplend.entities.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepaymentRepository extends JpaRepository<Repayment, Long> {
    
    List<Repayment> findByLoanId(Long loanId);

    Optional<Repayment> findByTransactionReference(String transactionReference);

    boolean existsByTransactionReference(String transactionReference);
    
}
