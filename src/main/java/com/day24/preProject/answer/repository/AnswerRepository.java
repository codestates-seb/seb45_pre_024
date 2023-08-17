package com.day24.preProject.answer.repository;

import com.day24.preProject.answer.entity.Answer;
import com.day24.preProject.answer.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    //유저
    @Query("SELECT q FROM Answer q WHERE q.question = :question_id AND q.deleted = :deleted")
    Page<Answer> findByQuestion_idAndDeleted(long question_id, boolean deleted, Pageable pageable);

    @Query("SELECT q FROM Answer q WHERE q.answer_id = :answer_id AND q.deleted = :deleted")
    Optional<Answer> findByAnswer_idAndDeleted(long answer_id, boolean deleted);


    Answer findAllByDeleted(boolean deleted);
    

}
