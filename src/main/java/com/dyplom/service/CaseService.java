package com.dyplom.service;

import com.dyplom.entity.Case;
import com.dyplom.entity.Question;

import java.util.List;

public interface CaseService {
    void saveFromQuestion(Case aCase, Question question);
    List<Case> findCasesByQuestion(Question question);
    void delete(Long id);
}
