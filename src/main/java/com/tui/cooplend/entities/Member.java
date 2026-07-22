package com.tui.cooplend.entities;


import com.tui.cooplend.enums.MemberStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_number", nullable = false, unique = true)
    private String memberNumber;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "national_id", nullable = false, unique = true)
    private String nationalId;

    @Column(nullable = false)
    private String telephone;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MemberStatus status = MemberStatus.ACTIVE;

    public void suspend(){
        this.status = MemberStatus.SUSPENDED;
    }

    public void activate(){
        this.status = MemberStatus.ACTIVE;
    }

    public boolean isActive(){
        return this.status == MemberStatus.ACTIVE;
    }
}
