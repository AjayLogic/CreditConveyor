package com.dyplom.repository;

import com.dyplom.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("questionRepository")
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findByQuestionName(String questionName);
}
