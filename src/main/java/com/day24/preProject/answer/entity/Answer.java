package com.day24.preProject.answer.entity;

import com.day24.preProject.audit.Auditable;
import com.day24.preProject.member.entity.Member;
import com.day24.preProject.question.entity.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Answer extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answer_id;

    @Column(nullable = false, updatable = false)
    private long member_id;

    @Column(nullable = false, updatable = false)
    private long question_id;

    @Column(nullable = false)
    private String body;

    @Column
    private boolean accepted = false;

    @Column
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    public void addQuestion(Question question) {
        this.question = question;
    }

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public void addMember(Member member) { this.member = member; }

}
