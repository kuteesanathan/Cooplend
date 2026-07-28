package com.tui.cooplend.services;

import com.tui.cooplend.commonerrors.BusinessRuleViolationException;
import com.tui.cooplend.commonerrors.DuplicateResourceException;
import com.tui.cooplend.commonerrors.ResourceNotFoundException;
import com.tui.cooplend.dtos.MemberRequest;
import com.tui.cooplend.dtos.MemberResponse;
import com.tui.cooplend.dtos.MemberUpdateRequest;
import com.tui.cooplend.entities.Member;
import com.tui.cooplend.mappers.MemberMapper;
import com.tui.cooplend.repositories.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@Getter
@Setter
@Builder
@AllArgsConstructor
public class MemberService {
    private static final SecureRandom RANDOM = new SecureRandom();
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public MemberResponse register(MemberRequest request){
        if (memberRepository.existsByNationalId(request.nationalId())){
            throw new DuplicateResourceException("A member with national ID " + request.nationalId() + " already exists");
        }
        Member member = Member.builder()
                .memberNumber(generateUniqueMemberNumber())
                .fullName(request.fullName())
                .nationalId(request.nationalId())
                .dateOfBirth(request.dateOfBirth())
                .status(request.status())
                .build();
        return memberMapper.toResponse(memberRepository.save(member));
    }

    public MemberResponse getById(Long id){
        return memberMapper.toResponse(findOrThrow(id));
    }

//    public Page<MemberResponse> search(String term, Pageable pageable){
//        return memberRepository.search(term, pageable).map(memberMapper::toResponse);
//    }

    @Transactional
    public MemberResponse update(Long id, MemberUpdateRequest request){
        Member member = findOrThrow(id);
        member.setFullName(request.fullname());
        member.setTelephone(request.telephone());
        return memberMapper.toResponse(member);
    }

    @Transactional
    public MemberResponse suspend(Long id){
        Member member = findOrThrow(id);
        member.suspend();
        return memberMapper.toResponse(member);
    }

    @Transactional
    public MemberResponse activate(Long id){
        Member member = findOrThrow(id);
        member.activate();
        return memberMapper.toResponse(member);
    }

    Member findOrThrow(Long id){
        return memberRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Member " + id + " not found"));
    }

    private String generateUniqueMemberNumber() {
        String candidate;
        int attempts = 0;
        do {
            candidate = "MB-" + String.format("%08d", RANDOM.nextInt(100_000_000));
            attempts++;
            if (attempts > 20) {
                throw new BusinessRuleViolationException("MEMBER_NUMBER_GENERATION_FAILED", "Could not generate a unique please retry");
            }
        } while (memberRepository.existsByMemberNumber(candidate));
        return candidate;
    }
}
