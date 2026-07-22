package com.tui.cooplend.services;

import com.tui.cooplend.commonerrors.BusinessRuleViolationException;
import com.tui.cooplend.commonerrors.ResourceNotFoundException;
import com.tui.cooplend.dtos.LoanProductRequest;
import com.tui.cooplend.dtos.LoanProductResponse;
import com.tui.cooplend.dtos.LoanProductUpdateRequest;
import com.tui.cooplend.entities.LoanProduct;
import com.tui.cooplend.mappers.LoanProductMapper;
import com.tui.cooplend.repositories.LoanProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanProductService {

    private final LoanProductRepository loanProductRepository;

    private final LoanProductMapper loanProductMapper;

    @Transactional
    public LoanProductResponse create(LoanProductRequest request){
        validateLimits(request.minimumAmount(), request.minimumAmount(), request.minimumTermMonths(), request.maximumTermMonths());

//        if (loanProductRepository.existsByCode(request.code())){
//            throw new DuplicateResourceException("A loan product with code " + request.code() + " already exists");
//        }
        LoanProduct product = LoanProduct.builder()
                .code(request.code())
                .name(request.name())
                .minimumAmount(request.minimumAmount())
                .maximumAmount(request.maximumAmount())
                .annualInterestRate(request.annualInterestRate())
                .minimumTermMonths(request.minimumTermMonths())
                .maximumTermMonths(request.maximumTermMonths())
                .active(false)
                .build();
        return LoanProductMapper.toResponse(loanProductRepository.save(product));
    }

    public LoanProductResponse getById(Long id){
        return LoanProductMapper.toResponse(findOrThrow(id));
    }

    public List<LoanProductResponse> list(){
        return loanProductRepository.findAll().stream().map(LoanProductMapper::toResponse).toList();
    }
    @Transactional
    public  LoanProductResponse update(Long id, LoanProductUpdateRequest request) {
        validateLimits(request.minimumAmount(), request.maximumAmount(), request.minimumTermMonths(), request.maximumTermMonths());
        LoanProduct product = findOrThrow(id);
        product.setName(request.name());
        product.setMinimumAmount(request.minimumAmount());
        product.setMaximumAmount(request.maximumAmount());
        product.setAnnualInterestRate(request.annualInterestRate());
        product.setMinimumTermMonths(request.minimumTermMonths());
        product.setMaximumTermMonths(request.maximumTermMonths());

        return LoanProductMapper.toResponse(product);
    }
    @Transactional
    public LoanProductResponse activate(Long id){
        LoanProduct product = findOrThrow(id);
        product.activate();
        return LoanProductMapper.toResponse(product);
    }
    @Transactional
    public LoanProductResponse deactivate(){
        LoanProduct product = findOrThrow(id);
        product.deactivate();
        return LoanProductMapper.toResponse(product);
    }

    LoanProduct findOrThrow(Long id){
        return loanProductRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loan product " + id + " not found"));
    }

    private void validateLimits(BigDecimal minimumAmount, BigDecimal maximumAmount, Integer minimumTermMonths, Integer maximumTermMonths){
        if (minimumAmount.compareTo(maximumAmount) >= 0){
            throw new BusinessRuleViolationException("INVALID_PRODUCT_LIMITS", "minimumAmount must be below maximumAmount");
        }
        if (minimumTermMonths >= maximumTermMonths){
            throw new BusinessRuleViolationException("INVALID_PRODUCT_LIMITS", "minimumTermMonths must be below maximumTermMonths");
        }
    }

}
