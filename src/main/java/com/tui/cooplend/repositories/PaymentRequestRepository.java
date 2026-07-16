package com.tui.cooplend.repositories;

import com.tui.cooplend.entities.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {
    List<PaymentRequest> findByLoanId(Long loanId);

    Optional<PaymentRequest> findByInternalReference(String internalReference);

    boolean existsByInternalReference(String internalReference);
}
