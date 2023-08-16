package com.day24.preProject.answerComment.entity;

import com.day24.preProject.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class AnswerComment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answerReply_id;
    @Column(nullable = false, updatable = false)
    private long answer_id;
    @Column(nullable = false, updatable = false)
    private long member_id;
    @Column(nullable = false, updatable = false)
    private String body;
    @Column
    private boolean deleted = false;
}
