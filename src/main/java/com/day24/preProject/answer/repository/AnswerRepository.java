package com.day24.preProject.answer.repository;

import com.day24.preProject.answer.entity.Answer;
import com.day24.preProject.answer.entity.Answer;
import com.day24.preProject.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    //유저
    @Query("SELECT q FROM Answer q WHERE q.question = :questionId AND q.deleted = :deleted")
    Page<Answer> findByQuestionIdAndDeleted(long questionId, boolean deleted, Pageable pageable);

    @Query("SELECT q FROM Answer q WHERE q.answerId = :answerId AND q.deleted = :deleted")
    Optional<Answer> findByAnswerIdAndDeleted(long answerId, boolean deleted);

    @Modifying
    @Query("UPDATE Answer q SET q.accepted = CASE WHEN q.accepted = true THEN false ELSE true END WHERE q = :answer")
    void changeAcceptedAndSave(@Param("answer") Answer answer);
    Answer findAllByDeleted(boolean deleted);
    

}
