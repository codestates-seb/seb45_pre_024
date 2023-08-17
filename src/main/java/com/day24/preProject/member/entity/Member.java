package com.day24.preProject.member.entity;

import com.day24.preProject.answer.entity.Answer;
import com.day24.preProject.audit.Auditable;
import com.day24.preProject.question.entity.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Answer> answers = new ArrayList<>();

    public void setQuestion(Question question) {
        questions.add(question);
        if (question.getMember() != this) {
            question.setMember(this);
        }
    }

    public void setAnswer(Answer answer) {
        answers.add(answer);
        if (answer.getMember() != this) {
            answer.setMember(this);
        }
    }

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
