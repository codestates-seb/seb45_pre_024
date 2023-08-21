package com.day24.preProject.question.repository;

import com.day24.preProject.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE q.questionId = :questionId AND q.deleted = :deleted ")
    Optional<Question> findByQuestionIdAndDeleted(long questionId, boolean deleted);

    @Query("SELECT q FROM Question q WHERE (q.title LIKE %:text% OR q.body LIKE %:text%) AND q.deleted = :deleted")
    Page<Question> findByTitleOrBody(@Param("text") String text, boolean deleted, Pageable pageable);

    @Modifying
    @Query("UPDATE Question q SET q.view_count = q.view_count + 1 WHERE q = :question")
    void incrementViewCountAndSave(@Param("question") Question question);

    @Modifying
    @Query("UPDATE Question q SET q.accepted = CASE WHEN q.accepted = true THEN false ELSE true END WHERE q = :question")
    void changeAcceptedAndSave(@Param("question") Question question);

    Page<Question> findAllByDeleted(boolean deleted, Pageable pageable);
}