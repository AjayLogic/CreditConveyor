package com.dyplom.service;

import com.dyplom.entity.ScoringMap;

import java.util.List;

public interface ScoringMapService {
    List<ScoringMap> findAll();
    void save(ScoringMap scoringMap);
    ScoringMap findOne(Long id);
    List<ScoringMap> findByIsActive(boolean isActive);
    void delete(long id);
}
