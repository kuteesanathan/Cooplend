package com.tui.cooplend.mappers;

import com.tui.cooplend.dtos.MemberResponse;
import com.tui.cooplend.entities.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberResponse toResponse(Member member);

}
