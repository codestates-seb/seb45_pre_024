package com.day24.preProject.AnswerReply.repository;

import com.day24.preProject.answer.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerReplyRepository {

    Page<AnswerReply> findAll(PageRequest answerReplyId);

    Optional<AnswerReply> findById(long answerReplyId);

    public interface AnswerRepository extends JpaRepository<Answer, Long> {
        @Override
        List<Answer> findAll();

        @Override
        Optional<Answer> findById(Long id);

    }

}
