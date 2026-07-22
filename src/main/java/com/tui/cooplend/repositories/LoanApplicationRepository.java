package com.tui.cooplend.repositories;

import com.tui.cooplend.entities.LoanApplication;
import com.tui.cooplend.entities.LoanProduct;
import com.tui.cooplend.entities.Member;
import com.tui.cooplend.enums.LoanApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    List<LoanApplication> findByMemberId(Member memberId);

    List<LoanApplication> findByStatus(LoanApplicationStatus status);

    boolean existsByMemberAndProductAndStatus(Member member, LoanProduct product, LoanApplicationStatus status);

    @EntityGraph(attributePaths = {"member", "product"})
    @Query("select a from LoanApplication a")
    Page<LoanApplication> findAllWithMemberAndProduct(Pageable pageable);

    @EntityGraph(attributePaths = {"member", "product", "reviewer"})
    Optional<LoanApplication> findWithDetailsById(Long id);
}
