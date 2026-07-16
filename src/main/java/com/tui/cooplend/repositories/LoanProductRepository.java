package com.tui.cooplend.repositories;

import com.tui.cooplend.entities.LoanProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanProductRepository extends JpaRepository<LoanProduct, Long> {

    Optional<LoanProduct> findByCode(String code);

    boolean existsByCode(String code);
}
