package com.day24.preProject.questionComment.entity;

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
public class QuestionComment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long questionCommentId;

    @Column(nullable = false, updatable = false)
    private String body;

    @Column
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "MEMBER", updatable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "QUESTION", updatable = false)
    private Question question;


}
