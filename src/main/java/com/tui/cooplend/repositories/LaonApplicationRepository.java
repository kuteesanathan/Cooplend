package com.tui.cooplend.repositories;

import com.tui.cooplend.entities.LoanApplication;
import com.tui.cooplend.entities.Member;
import com.tui.cooplend.enums.LoanApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LaonApplicationRepository extends JpaRepository<LoanApplication, Long> {
    List<LoanApplication> findByMemberId(Member memberId);

    List<LoanApplication> findByStatus(LoanApplicationStatus status);
}
