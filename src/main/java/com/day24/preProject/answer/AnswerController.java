package com.day24.preProject.answer;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class AnswerController {

    public class Answer {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long answer_id;
        @Column
        private long member_id;
        @Column
        private long question_id;
        @Column
        private String body;
        @Column
        private boolean accepted;
        @Column
        private LocalDateTime created_at;
        @Column
        private LocalDateTime modified_at;
    }
}
