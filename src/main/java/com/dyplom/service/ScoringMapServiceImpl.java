package com.dyplom.service;

import com.dyplom.entity.Case;
import com.dyplom.entity.Question;
import com.dyplom.entity.ScoringMap;
import com.dyplom.repository.ScoringMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("scoringMapService")
public class ScoringMapServiceImpl implements ScoringMapService {

    @Autowired
    ScoringMapRepository scoringMapRepository;

    @Override
    public List<ScoringMap> findAll() {
        return scoringMapRepository.findAll();
    }

    @Override
    public void save(ScoringMap scoringMap) {

        ArrayList<Integer> scores = new ArrayList<>();
        int minSum = 0;
        int min = 0;

        for (Question q : scoringMap.getQuestionList()){
            for (Iterator<Case> c = q.getCaseList().iterator(); c.hasNext(); ) {
                Case iteratorCase = c.next();
                if(iteratorCase.getScores() > 0 ) {
                    scores.add(iteratorCase.getScores());
                }
            }

            min = Collections.min(scores);
            minSum += min;
            scores.clear();
        }

        scoringMap.setDateOfCreating(new Date());
        scoringMap.setMinScores(minSum);
        scoringMapRepository.save(scoringMap);
    }

    @Override
    public ScoringMap findOne(Long id) {
        return scoringMapRepository.findOne(id);
    }

    @Override
    public List<ScoringMap> findByIsActive(boolean isActive) {
        return scoringMapRepository.findByIsActive(isActive);
    }

    @Override
    public void delete(long id) {
        scoringMapRepository.delete(id);
    }

}
