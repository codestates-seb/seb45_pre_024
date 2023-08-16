package com.day24.preProject.answerReply.repository;

import com.day24.preProject.answer.entity.Answer;
import com.day24.preProject.answerReply.entity.AnswerReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerReplyRepository extends JpaRepository<AnswerReply, Long> {

    Page<AnswerReply> findAll(PageRequest answerReplyId);

    Optional<AnswerReply> findById(long answerReplyId);
}
