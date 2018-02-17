package com.dyplom.entity;

import org.hibernate.annotations.Type;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "scoring_map")
public class ScoringMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "date_of_creating")
    private Date dateOfCreating;
    @Column(name = "min_scores")
    private int minScores;
    @Column(name = "description")
    private String description;
    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isActive;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "scoringMap")
    private List<TestResult> testResultList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "scoring_map_has_question", joinColumns = @JoinColumn(name = "scoring_map_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"))
    private List<Question> questionList;

    public ScoringMap() {
    }

    public ScoringMap(Date dateOfCreating, int minScores, String description, boolean isActive, List<TestResult> testResultList, List<Question> questionList) {
        this.dateOfCreating = dateOfCreating;
        this.minScores = minScores;
        this.description = description;
        this.isActive = isActive;
        this.testResultList = testResultList;
        this.questionList = questionList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateOfCreating() {
        return dateOfCreating;
    }

    public void setDateOfCreating(Date dateOfCreating) {
        this.dateOfCreating = dateOfCreating;
    }

    public int getMinScores() {
        return minScores;
    }

    public void setMinScores(int minScores) {
        this.minScores = minScores;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<TestResult> getTestResultList() {
        return testResultList;
    }

    public void setTestResultList(List<TestResult> testResultList) {
        this.testResultList = testResultList;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    @Override
    public String toString() {
        return "ScoringMap{" +
                "id=" + id +
                ", dateOfCreating=" + dateOfCreating +
                ", minScores=" + minScores +
                ", descrption='" + description + '\'' +
                ", isActive=" + isActive +
                ", testResultList=" + testResultList +
                ", questionList=" + questionList +
                '}';
    }
}

