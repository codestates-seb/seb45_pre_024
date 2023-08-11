package com.day24.preProject.question.entity;

import com.day24.preProject.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Question extends Auditable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long question_id;

        @Column(nullable = false, updatable = false)
        private long member_id;

        @Column(nullable = false, length = 100)
        private String title;

        @Column(nullable = false)
        private String body;

        @Column
        private int view_count = 0;

        @Column
        private boolean accepted = false;

        @Column
        private boolean deleted = false;

}
