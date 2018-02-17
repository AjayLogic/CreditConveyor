package com.dyplom.service;

import com.dyplom.entity.Question;
import com.dyplom.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Question findByQuestionName(String questionName) {
        return questionRepository.findByQuestionName(questionName);
    }

    @Override
    public Question findOne(long id) {
        return questionRepository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        questionRepository.delete(id);
    }
}
