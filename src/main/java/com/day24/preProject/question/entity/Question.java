package com.day24.preProject.question.entity;

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
public class Question extends Auditable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long questionId;


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

        @ManyToOne
        @JoinColumn(name = "MEMBER", updatable = false)
        private Member member;

        @OneToMany(mappedBy = "question")
        private List<Answer> answers = new ArrayList<>();

        public void setMember(Member member) {this.member = member;}

        public void  addAnswer(Answer answer) {
                answers.add(answer);
                if (answer.getQuestion() != this) {
                        answer.setQuestion(this);
                }
        }

}
