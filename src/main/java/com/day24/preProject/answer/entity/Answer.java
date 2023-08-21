package com.day24.preProject.answer.entity;

import com.day24.preProject.answerComment.entity.AnswerComment;
import com.day24.preProject.audit.Auditable;
import com.day24.preProject.member.entity.Member;
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
public class Answer extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answerId;

    @Column(nullable = false)
    private String body;

    @Column
    private boolean accepted = false;

    @Column
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "QUESTION", updatable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "MEMBER", updatable = false)
    private Member member;

    @OneToMany(mappedBy = "answer")
    private List<AnswerComment> answerComments = new ArrayList<>();

}
