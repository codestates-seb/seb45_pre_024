package com.day24.preProject.question.repository;

import com.day24.preProject.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE q.question_id = :question_id AND q.deleted = :deleted ")
    Optional<Question> findByQuestion_idAndDeleted(long question_id, boolean deleted);

    Page<Question> findAllByDeleted(boolean deleted, Pageable pageable);
}