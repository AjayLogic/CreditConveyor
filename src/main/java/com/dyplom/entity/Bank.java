package com.dyplom.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "mfo")
    private String mfo;
    @Column(name = "adress")
    private String adress;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bank")
    private List<BankWorker> bankWorkers;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bank")
    private List<CreditProduct> creditProducts;

    public Bank() {
    }

    public Bank(String name, String mfo, String adress, List<BankWorker> bankWorkers, List<CreditProduct> creditProducts) {
        this.name = name;
        this.mfo = mfo;
        this.adress = adress;
        this.bankWorkers = bankWorkers;
        this.creditProducts = creditProducts;
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

    public String getMfo() {
        return mfo;
    }

    public void setMfo(String mfo) {
        this.mfo = mfo;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public List<BankWorker> getBankWorkers() {
        return bankWorkers;
    }

    public void setBankWorkers(List<BankWorker> bankWorkers) {
        this.bankWorkers = bankWorkers;
    }

    public List<CreditProduct> getCreditProducts() {
        return creditProducts;
    }

    public void setCreditProducts(List<CreditProduct> creditProducts) {
        this.creditProducts = creditProducts;
    }

}
