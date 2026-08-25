package com.tui.cooplend.mappers;

import com.tui.cooplend.dtos.LoanResponse;
import com.tui.cooplend.entities.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    @Mapping(target = "applicationId", source = "applicationId.id")
    @Mapping(target = "memberId", source = "applicationId.member.id")
    @Mapping(target = "productName", source = "applicationId.product.id")
    @Mapping(target = "memberName", source = "applicationId.member.fullName")
    LoanResponse toResponse(Loan loan);

}
