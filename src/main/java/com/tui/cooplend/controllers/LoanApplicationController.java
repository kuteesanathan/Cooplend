package com.tui.cooplend.controllers;


import com.tui.cooplend.entities.LoanApplication;
import com.tui.cooplend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/loan-applications")
@RequiredArgsConstructor
public class LoanApplicationController {

        private final LoanApplicationRepository loanApplicationRepository;
        private final MemberRepository memberRepository;
        private final LoanProductRepository loanProductRepository;
        private final UserRepository userRepository;

        @GetMapping
        public List<LoanApplicationResponse> getAll(
                @RequestParam(required = false) Long memberId,
                @RequestParam(required = false) LoanApplicationStatus status) {
            List<LoanApplication> applications;
            if (memberId != null) {
                applications = loanApplicationRepository.findByMemberId(memberId);
            } else if (status != null) {
                applications = loanApplicationRepository.findByStatus(status);
            } else {
                applications = loanApplicationRepository.findAll();
            }
            return applications.stream().map(this::toResponse).toList();
        }

        @GetMapping("/{id}")
        public LoanApplicationResponse getById(@PathVariable Long id) {
            return toResponse(findApplicationOrThrow(id));
        }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public LoanApplicationResponse create(@Valid @RequestBody LoanApplicationRequest request) {
            Member member = memberRepository.findById(request.memberId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found: " + request.memberId()));
            LoanProduct product = loanProductRepository.findById(request.productId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan product not found: " + request.productId()));

            if (request.amount().compareTo(product.getMinimumAmount()) < 0
                    || request.amount().compareTo(product.getMaximumAmount()) > 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Amount must be between " + product.getMinimumAmount() + " and " + product.getMaximumAmount());
            }
            if (request.termMonths() < product.getMinimumTermMonths()
                    || request.termMonths() > product.getMaximumTermMonths()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Term must be between " + product.getMinimumTermMonths() + " and " + product.getMaximumTermMonths() + " months");
            }

            LoanApplication application = LoanApplication.builder()
                    .member(member)
                    .product(product)
                    .amount(request.amount())
                    .termMonths(request.termMonths())
                    .purpose(request.purpose())
                    .status(LoanApplicationStatus.PENDING)
                    .assessmentResult(AssessmentResult.NOT_ASSESSED)
                    .submittedDate(LocalDateTime.now())
                    .build();
            return toResponse(loanApplicationRepository.save(application));
        }

        @PutMapping("/{id}/review")
        public LoanApplicationResponse review(@PathVariable Long id, @Valid @RequestBody LoanApplicationReviewRequest request) {
            LoanApplication application = findApplicationOrThrow(id);
            User reviewer = userRepository.findById(request.reviewerId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reviewer not found: " + request.reviewerId()));

            application.setStatus(request.status());
            application.setAssessmentResult(request.assessmentResult());
            application.setReviewReason(request.reviewReason());
            application.setReviewer(reviewer);
            application.setReviewedDate(LocalDateTime.now());
            return toResponse(loanApplicationRepository.save(application));
        }

        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void delete(@PathVariable Long id) {
            loanApplicationRepository.delete(findApplicationOrThrow(id));
        }

        private LoanApplication findApplicationOrThrow(Long id) {
            return loanApplicationRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan application not found: " + id));
        }

        private LoanApplicationResponse toResponse(LoanApplication application) {
            return new LoanApplicationResponse(
                    application.getId(),
                    application.getMember().getId(),
                    application.getMember().getFullName(),
                    application.getProduct().getId(),
                    application.getProduct().getName(),
                    application.getAmount(),
                    application.getTermMonths(),
                    application.getPurpose(),
                    application.getStatus(),
                    application.getAssessmentResult(),
                    application.getReviewReason(),
                    application.getSubmittedDate(),
                    application.getReviewedDate(),
                    application.getReviewer() == null ? null : application.getReviewer().getId()
            );
        }
    }
}
