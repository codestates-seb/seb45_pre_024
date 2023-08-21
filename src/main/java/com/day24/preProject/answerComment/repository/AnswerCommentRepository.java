package com.day24.preProject.answerComment.repository;

import com.day24.preProject.answer.entity.Answer;
import com.day24.preProject.answerComment.entity.AnswerComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AnswerCommentRepository extends JpaRepository<AnswerComment, Long> {

    @Query("SELECT q FROM AnswerComment q WHERE q.answer = :answerId AND q.deleted = :deleted")
    Page<AnswerComment> findByAnswerIdAndDeleted(long answerId, boolean deleted, Pageable pageable);

    @Query("SELECT q FROM AnswerComment q WHERE q.answerCommentId = :answerCommentId AND q.deleted = :deleted")
    Optional<AnswerComment> findByAnswerCommentIdAndDeleted(long answerCommentId, boolean deleted);

    Page<AnswerComment> findAll(Pageable pageable);

    Optional<AnswerComment> findById(long answerCommentId);
}
