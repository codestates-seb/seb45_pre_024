package com.day24.preProject.question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository {

    @Override
    List<QuestionController.Question> findAll();

    @Override
    Optional<QuestionController.Question> findById(Integer integer);
}
