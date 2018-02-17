package com.dyplom.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endtDate;
    @Column(name = "sum")
    private BigDecimal sum;
    @Column(name = "purpose")
    private String purpose;
    @Column(name = "is_checked", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isChecked;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_worker_id")
    private BankWorker bankWorker;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_product_id", nullable = false)
    private CreditProduct creditProduct;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contract")
    private List<Payment> paymentList;

    public Contract() {
    }

    public Contract(Date startDate, Date endtDate, BigDecimal sum, String purpose, boolean isChecked, Client client, BankWorker bankWorker, CreditProduct creditProduct, List<Payment> paymentList) {
        this.startDate = startDate;
        this.endtDate = endtDate;
        this.sum = sum;
        this.purpose = purpose;
        this.isChecked = isChecked;
        this.client = client;
        this.bankWorker = bankWorker;
        this.creditProduct = creditProduct;
        this.paymentList = paymentList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndtDate() {
        return endtDate;
    }

    public void setEndtDate(Date endtDate) {
        this.endtDate = endtDate;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BankWorker getBankWorker() {
        return bankWorker;
    }

    public void setBankWorker(BankWorker bankWorker) {
        this.bankWorker = bankWorker;
    }

    public CreditProduct getCreditProduct() {
        return creditProduct;
    }

    public void setCreditProduct(CreditProduct creditProduct) {
        this.creditProduct = creditProduct;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endtDate=" + endtDate +
                ", sum=" + sum +
                ", purpose='" + purpose + '\'' +
                ", isChecked=" + isChecked +
                ", client=" + client +
                ", bankWorker=" + bankWorker +
                ", creditProduct=" + creditProduct +
                ", paymentList=" + paymentList +
                '}';
    }
}

