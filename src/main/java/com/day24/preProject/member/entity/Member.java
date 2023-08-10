package com.day24.preProject.member.entity;

import com.day24.preProject.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Member extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, updatable = false, unique = true)
    private String username;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    public enum MemberStatus{
        MEMBER_ACTIVE("활동중"),
        MEMBER_ANAUTHORIZED("미인증 상태"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;
        MemberStatus(String status) {
            this.status = status;
        }
    }
}
