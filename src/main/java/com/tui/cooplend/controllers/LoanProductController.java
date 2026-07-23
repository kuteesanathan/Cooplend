package com.tui.cooplend.controllers;

import com.tui.cooplend.dtos.LoanProductRequest;
import com.tui.cooplend.dtos.LoanProductResponse;
import com.tui.cooplend.dtos.LoanProductUpdateRequest;
import com.tui.cooplend.services.LoanProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-products")
@AllArgsConstructor
public class LoanProductController {
    private final LoanProductService loanProductService;

    @PostMapping
    public ResponseEntity<LoanProductResponse> create(@Valid @RequestBody LoanProductRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(loanProductService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<LoanProductResponse>>list(){

        return ResponseEntity.ok(loanProductService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanProductResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(loanProductService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanProductResponse> update(@PathVariable Long id, @Valid @RequestBody LoanProductUpdateRequest request){
        return ResponseEntity.ok(loanProductService.update(id, request));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<LoanProductResponse> activate(@PathVariable Long id){
        return ResponseEntity.ok(loanProductService.activate(id));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<LoanProductResponse> deactivate(@PathVariable Long id){
        return ResponseEntity.ok(loanProductService.deactivate(id));
    }
}
