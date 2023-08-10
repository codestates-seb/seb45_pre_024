package com.day24.preProject.question;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    
    private final QuestionRepository questionRepository;
    public QuestionService(QuestionRepository questionRepository) { this.questionRepository = questionRepository;}

    public QuestionController.Question createQuestion(QuestionController.Question question){

        return questionRepository.save(question);
    }

    public List<QuestionController.Question> findAllQuestion(){
        return questionRepository.findAll();
    }

    public QuestionController.Question findQuestion(int id){
        return findVerifiedQuestionByQuery(id);
    }

    public void deleteQuestion(int id){
        QuestionController.Question question = findVerifiedQuestionByQuery(id);
        questionRepository.delete(question);
    }

    public void deleteAllQuestion(){
        questionRepository.deleteAll();
    }


    public QuestionController.Question updateQuestion(QuestionController.Question question){
        QuestionController.Question findQuestion = findVerifiedQuestionByQuery(question.getId());

        Optional.ofNullable(question.getTitle())
                .ifPresent(title -> findQuestion.setTitle(title));
        Optional.ofNullable(question.getTodoOrder())
                .ifPresent(eodoOrder -> findQuestion.setTodoOrder(eodoOrder));
        Optional.ofNullable(question.isCompleted())
                .ifPresent(completed -> findQuestion.setCompleted(completed));


        return questionRepository.save(findQuestion);
    }

    private Question findVerifiedQuestionByQuery(int id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        Question findQuestion =
                optionalQuestion.orElse(null);

        return findQuestion;
    }
}
