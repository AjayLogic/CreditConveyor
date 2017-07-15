package com.dyplom.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "question_name")
    private String questionName;
    @ManyToMany(mappedBy = "questionList")
    private List<ScoringMap> scoringMapList;
    @OneToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST}, mappedBy = "question")
    private List<Case> caseList;

    public Question() {
    }

    public Question(String questionName, List<ScoringMap> scoringMapList, List<Case> caseList) {
        this.questionName = questionName;
        this.scoringMapList = scoringMapList;
        this.caseList = caseList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public List<ScoringMap> getScoringMap() {
        return scoringMapList;
    }

    public void setScoringMap(List<ScoringMap> scoringMapList) {
        this.scoringMapList = scoringMapList;
    }

    public List<Case> getCaseList() {
        return caseList;
    }

    public void setCaseList(List<Case> caseList) {
        this.caseList = caseList;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + questionName + '\'' +
                ", scoringMap=" + scoringMapList +
                ", caseList=" + caseList +
                '}';
    }
}

