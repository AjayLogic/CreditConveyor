package com.dyplom.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "test_result")
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_of_testing")
    private Date dateOfTesting;
    @Column(name = "result_score")
    private int resultScore;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scoring_map_id", nullable = false)
    private ScoringMap scoringMap;
    @ManyToMany
    @JoinTable(name = "case_has_test_result", joinColumns = @JoinColumn(name = "test_result_id"),
            inverseJoinColumns = @JoinColumn(name = "case_id"))
    private List<Case> caseList;

    public TestResult() {
    }

    public TestResult(Date dateOfTesting, int resultScore, Client client, ScoringMap scoringMap, List<Case> caseList) {
        this.dateOfTesting = dateOfTesting;
        this.resultScore = resultScore;
        this.client = client;
        this.scoringMap = scoringMap;
        this.caseList = caseList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateOfTesting() {
        return dateOfTesting;
    }

    public void setDateOfTesting(Date dateOfTesting) {
        this.dateOfTesting = dateOfTesting;
    }

    public int getResultScore() {
        return resultScore;
    }

    public void setResultScore(int resultScore) {
        this.resultScore = resultScore;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ScoringMap getScoringMap() {
        return scoringMap;
    }

    public void setScoringMap(ScoringMap scoringMap) {
        this.scoringMap = scoringMap;
    }

    public List<Case> getCaseList() {
        return caseList;
    }

    public void setCaseList(List<Case> caseList) {
        this.caseList = caseList;
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "id=" + id +
                ", dateOfTesting=" + dateOfTesting +
                ", resultScore=" + resultScore +
                ", client=" + client +
                ", scoringMap=" + scoringMap +
                ", caseList=" + caseList +
                '}';
    }
}

