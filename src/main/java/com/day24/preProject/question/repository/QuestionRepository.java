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

    @Modifying
    @Query("UPDATE Question q SET q.view_count = q.view_count + 1 WHERE q = :question")
    void incrementViewCountAndSave(@Param("question") Question question);

    Page<Question> findAllByDeleted(boolean deleted, Pageable pageable);
}