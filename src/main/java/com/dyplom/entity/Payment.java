package com.dyplom.entity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "date_of_payment")
    private Date dateOfPayment;
    @Column(name = "state_of_payment")
    private String stateOfPayment;
    @Column(name = "sum_of_payment")
    private BigDecimal sumOfPayment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    public Payment() {
    }

    public Payment(Date dateOfPayment, String stateOfPayment, BigDecimal sumOfPayment, Contract contract) {
        this.dateOfPayment = dateOfPayment;
        this.stateOfPayment = stateOfPayment;
        this.sumOfPayment = sumOfPayment;
        this.contract = contract;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public String getStateOfPayment() {
        return stateOfPayment;
    }

    public void setStateOfPayment(String stateOfPayment) {
        this.stateOfPayment = stateOfPayment;
    }

    public BigDecimal getSumOfPayment() {
        return sumOfPayment;
    }

    public void setSumOfPayment(BigDecimal sumOfPayment) {
        this.sumOfPayment = sumOfPayment;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", dateOfPayment=" + dateOfPayment +
                ", stateOfPayment=" + stateOfPayment +
                ", sumOfPayment=" + sumOfPayment +
                ", contract=" + contract +
                '}';
    }
}

