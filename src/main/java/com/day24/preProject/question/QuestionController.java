package com.day24.preProject.question;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/question")
public class QuestionController {


    public class Question {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long question_id;
        @Column
        private long member_id;
        @Column
        private String title;
        @Column
        private String body;
        @Column
        private int view_count;
        @Column
        private boolean accepted;
        @Column
        private LocalDateTime created_at;
        @Column
        private LocalDateTime modified_at;

    }



}
