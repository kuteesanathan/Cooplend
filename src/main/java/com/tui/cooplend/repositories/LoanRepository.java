package com.tui.cooplend.repositories;

import com.tui.cooplend.entities.Loan;
import com.tui.cooplend.entities.LoanApplication;
import com.tui.cooplend.entities.Member;
import com.tui.cooplend.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByAccountNumber(String accountNumber);

    Optional<Loan> findByApplicationId(LoanApplication applicationId);

    List<Loan> findByStatus(LoanStatus status);

    List<Loan> findByApplicationIdMemberIdAndStatus(Long memberId, LoanStatus status);

    Optional<Loan> findWithApplicationById(Long id);

//    @Query("""
//select count(1), sum(case when 1.status = com.tui.cooplend.enums.LoanStatus.ACTIVE then 1 else 0 end),
//""")
    boolean existsByApplicationId(Long applicationId);
    boolean existsByAccountNumber(String accountNumber);

    CharSequence findByApplicationIdMemberIdAndStatus(LoanStatus status, Long id);
}
