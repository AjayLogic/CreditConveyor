package com.dyplom.service;

import com.dyplom.entity.Case;
import com.dyplom.entity.Question;
import com.dyplom.repository.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("caseService")
public class CaseServiceImpl implements CaseService {

    @Autowired
    CaseRepository caseRepository;

    @Override
    public void saveFromQuestion(Case aCase, Question question) {
        aCase.setQuestion(question);
        caseRepository.save(aCase);
    }

    @Override
    public List<Case> findCasesByQuestion(Question question) {
        return caseRepository.findCasesByQuestion(question);
    }

    @Override
    public void delete(Long id) {
        caseRepository.delete(id);
    }
}
