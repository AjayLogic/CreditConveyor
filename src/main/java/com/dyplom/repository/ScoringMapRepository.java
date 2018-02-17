package com.dyplom.repository;

import com.dyplom.entity.ScoringMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("scoringMapRepository")
public interface ScoringMapRepository extends JpaRepository<ScoringMap, Long> {
    List<ScoringMap> findByIsActive(boolean isActive);
}
