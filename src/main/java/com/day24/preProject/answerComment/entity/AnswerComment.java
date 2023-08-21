package com.day24.preProject.answerComment.entity;

import com.day24.preProject.answer.entity.Answer;
import com.day24.preProject.audit.Auditable;
import com.day24.preProject.member.entity.Member;
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
public class AnswerComment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answerCommentId;

    @Column(nullable = false, updatable = false)
    private String body;

    @Column
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "MEMBER", updatable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ANSWER", updatable = false)
    private Answer answer;

}
