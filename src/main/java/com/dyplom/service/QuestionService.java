package com.dyplom.service;


import com.dyplom.entity.Question;

import java.util.List;

public interface QuestionService {
    void save(Question question);
    List<Question> findAll();
    Question findByQuestionName(String questionName);
    Question findOne(long id);
    void delete(Long id);
}
