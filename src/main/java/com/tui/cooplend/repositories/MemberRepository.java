package com.tui.cooplend.repositories;

import com.tui.cooplend.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberNumber(String memberNumber);

    Optional<Member> findByNationalId(String nationalId);

    boolean existsByMemberNumber(String memberNumber);

    boolean existsByNationalId(String nationalId);

}
