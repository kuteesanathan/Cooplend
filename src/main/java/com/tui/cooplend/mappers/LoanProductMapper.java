package com.tui.cooplend.mappers;

import com.tui.cooplend.dtos.LoanProductResponse;
import com.tui.cooplend.entities.LoanProduct;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanProductMapper {
     LoanProductResponse toResponse(LoanProduct product);
}
