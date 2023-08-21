package com.day24.preProject.questionComment.repository;

import com.day24.preProject.questionComment.entity.QuestionComment;
import com.day24.preProject.questionComment.entity.QuestionComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuestionCommentRepository extends JpaRepository<QuestionComment, Long> {

    @Query("SELECT q FROM QuestionComment q WHERE q.question = :questionId AND q.deleted = :deleted")
    Page<QuestionComment> findByQuestionIdAndDeleted(long questionId, boolean deleted, Pageable pageable);

    @Query("SELECT q FROM QuestionComment q WHERE q.questionCommentId = :questionCommentId AND q.deleted = :deleted")
    Optional<QuestionComment> findByQuestionCommentIdAndDeleted(long questionCommentId, boolean deleted);

    Page<QuestionComment> findAll(Pageable pageable);

    Optional<QuestionComment> findById(long questionCommentId);
}
