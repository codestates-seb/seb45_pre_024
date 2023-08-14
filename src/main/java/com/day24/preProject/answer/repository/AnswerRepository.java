package com.day24.preProject.answer.repository;

import com.day24.preProject.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Override
    List<Answer> findAll();

    @Override
    Optional<Answer> findById(Long id);

}
