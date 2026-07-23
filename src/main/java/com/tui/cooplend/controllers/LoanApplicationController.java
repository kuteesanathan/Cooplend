package com.tui.cooplend.controllers;


import com.tui.cooplend.dtos.LoanApplicationRejectRequest;
import com.tui.cooplend.dtos.LoanApplicationRequest;
import com.tui.cooplend.dtos.LoanApplicationResponse;
import com.tui.cooplend.dtos.LoanResponse;
import com.tui.cooplend.entities.User;
import com.tui.cooplend.services.LoanApplicationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.unique.CreateTableUniqueDelegate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan-applications")
@AllArgsConstructor
public class LoanApplicationController {
    private final LoanApplicationService loanApplicationService;
    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanApplicationResponse> submit(@Valid @RequestBody LoanApplicationRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(loanApplicationService.submit(request));
    }

    @GetMapping
    public ResponseEntity<Page<LoanApplicationResponse>> list(Pageable pageable){
        return ResponseEntity.ok(loanApplicationService.list(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanApplicationResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(loanApplicationService.getById(id));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<LoanApplicationResponse> approve(@PathVariable Long id, @AuthenticationPrincipal User reviewer){
        return ResponseEntity.ok(loanApplicationService.approve(id, reviewer));
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<LoanApplicationResponse> reject(@PathVariable Long id, @Valid @RequestBody LoanApplicationRejectRequest request, @AuthenticationPrincipal User reviewer){
        return ResponseEntity.ok(loanApplicationService.reject(id, reviewer, request.reason()));
    }

    public ResponseEntity<LoanResponse> disburse(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.disburse(id))
    }
}