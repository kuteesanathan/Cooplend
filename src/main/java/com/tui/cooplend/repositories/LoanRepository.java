package com.tui.cooplend.repositories;

import com.tui.cooplend.entities.Loan;
import com.tui.cooplend.entities.LoanApplication;
import com.tui.cooplend.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByAccountNumber(String accountNumber);

    Optional<Loan> findByApplicationId(LoanApplication applicationId);

    List<Loan> findByStatus(LoanStatus status);

    boolean existsByAccountNumber(String accountNumber);
}
