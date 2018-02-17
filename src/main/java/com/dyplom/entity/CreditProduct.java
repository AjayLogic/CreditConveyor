package com.dyplom.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "credit_product")
public class CreditProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "percentage")
    private int percentage;
    @Column(name = "minSum")
    private BigDecimal minSum;
    @Column(name = "maxSum")
    private BigDecimal maxSum;
    @Column(name = "count_of_month")
    private int countOfMonth;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creditProduct")
    private List<Contract> contractList;

    public CreditProduct() {
    }

    public CreditProduct(String name, String description, int percentage, BigDecimal minSum, BigDecimal maxSum, int countOfMonth, Bank bank, List<Contract> contractList) {
        this.name = name;
        this.description = description;
        this.percentage = percentage;
        this.minSum = minSum;
        this.maxSum = maxSum;
        this.countOfMonth = countOfMonth;
        this.bank = bank;
        this.contractList = contractList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public BigDecimal getMinSum() {
        return minSum;
    }

    public void setMinSum(BigDecimal minSum) {
        this.minSum = minSum;
    }

    public BigDecimal getMaxSum() {
        return maxSum;
    }

    public void setMaxSum(BigDecimal maxSum) {
        this.maxSum = maxSum;
    }

    public int getCountOfMonth() {
        return countOfMonth;
    }

    public void setCountOfMonth(int countOfMonth) {
        this.countOfMonth = countOfMonth;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public List<Contract> getContractList() {
        return contractList;
    }

    public void setContractList(List<Contract> contractList) {
        this.contractList = contractList;
    }

    @Override
    public String toString() {
        return "CreditProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", percentage=" + percentage +
                ", minSum=" + minSum +
                ", maxSum=" + maxSum +
                ", bank=" + bank +
                ", contractList=" + contractList +
                '}';
    }
}

