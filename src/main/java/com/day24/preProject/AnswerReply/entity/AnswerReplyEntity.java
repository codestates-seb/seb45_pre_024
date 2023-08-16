package com.day24.preProject.AnswerReply.entity;

import com.day24.preProject.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class AnswerReplyEntity extends Auditable {

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
}
