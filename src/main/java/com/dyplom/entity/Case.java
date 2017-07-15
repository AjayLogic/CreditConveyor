package com.dyplom.entity;

import org.aspectj.weaver.ast.Test;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "case_option")
public class Case {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "case_item")
    private String caseItem;
    /*@Column(name = "other_case")
    private String otherCase;*/
    @Column(name = "scores")
    private int scores;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade =
                    {
                            CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.REFRESH,
                            CascadeType.PERSIST
                    },
            targetEntity = TestResult.class)
    @JoinTable(name = "case_has_test_result", joinColumns = @JoinColumn(name = "case_id"),
            inverseJoinColumns = @JoinColumn(name = "test_result_id"))
    private List<TestResult> testResultList;

    public Case() {
    }

    public Case(String caseItem,  int scores, Question question, List<TestResult> testResultList) {//String otherCase,
        this.caseItem = caseItem;
        //this.otherCase = otherCase;
        this.scores = scores;
        this.question = question;
        this.testResultList = testResultList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCaseItem() {
        return caseItem;
    }

    public void setCaseItem(String caseItem) {
        this.caseItem = caseItem;
    }

    /*public String getOtherCase() {
        return otherCase;
    }

    public void setOtherCase(String otherCase) {
        this.otherCase = otherCase;
    }*/

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<TestResult> getTestResultList() {
        return testResultList;
    }

    public void setTestResultList(List<TestResult> testResultList) {
        this.testResultList = testResultList;
    }

    @Override
    public String toString() {
        return "Case{" +
                "id=" + id +
                ", caseItem='" + caseItem + '\'' +
                //", otherCase='" + otherCase + '\'' +
                ", scores=" + scores +
                ", question=" + question +
                ", testResultList=" + testResultList +
                '}';
    }
}
