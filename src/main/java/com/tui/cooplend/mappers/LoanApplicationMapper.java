package com.tui.cooplend.mappers;

import com.tui.cooplend.dtos.LoanApplicationResponse;
import com.tui.cooplend.entities.LoanApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanApplicationMapper {
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "fullname", source = "member.fullName")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    LoanApplicationResponse toResponse(LoanApplication loanApplication);
}
