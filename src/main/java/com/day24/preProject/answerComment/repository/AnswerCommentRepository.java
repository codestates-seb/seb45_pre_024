package com.day24.preProject.answerComment.repository;

import com.day24.preProject.answerComment.entity.AnswerComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerCommentRepository extends JpaRepository<AnswerComment, Long> {

    Page<AnswerComment> findAll(Pageable pageable);

    Optional<AnswerComment> findById(long answerReplyId);
}
