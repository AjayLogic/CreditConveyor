package com.dyplom.repository;

import com.dyplom.entity.Case;
import com.dyplom.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("caseRepository")
public interface CaseRepository extends JpaRepository<Case, Long> {
    List<Case> findCasesByQuestion(Question question);
}
