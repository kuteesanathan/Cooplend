package com.tui.cooplend.controllers;

import com.tui.cooplend.dtos.LoanResponse;
import com.tui.cooplend.enums.LoanStatus;
import com.tui.cooplend.repositories.LoanRepository;
import com.tui.cooplend.services.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/loans")
@AllArgsConstructor
public class LoanController {
    private final LoanService loanService;

//    @GetMapping
//    public ResponseEntity<Page<LoanResponse>> search(
//            @RequestParam(required = false) LoanStatus status,
//            @RequestParam(required = false) Long memberId,
//            @RequestParam(required = false) Long productId,
//            @RequestParam(required = false) BigDecimal minBalance,
//            @RequestParam(required = false) BigDecimal maxBalance,
//            Pageable pageable) {
//        return ResponseEntity.ok(loanService.search(status, memberId, productId, minBalance, maxBalance, pageable));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getById(id));
    }
}
