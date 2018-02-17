package com.dyplom.creditCalculator;


import java.math.BigDecimal;

public class CreditCalcRow {
    private int numberOfMonth;
    private BigDecimal loanBalance;
    private BigDecimal creditBody;
    private BigDecimal payOfPercent;
    private BigDecimal payForMonth;

    public CreditCalcRow() {
    }

    public CreditCalcRow(int numberOfMonth, BigDecimal loanBalance, BigDecimal creditBody, BigDecimal payOfPercent, BigDecimal payForMonth) {
        this.numberOfMonth = numberOfMonth;
        this.loanBalance = loanBalance;
        this.creditBody = creditBody;
        this.payOfPercent = payOfPercent;
        this.payForMonth = payForMonth;
    }

    public int getNumberOfMonth() {
        return numberOfMonth + 1;
    }

    public void setNumberOfMonth(int numberOfMonth) {
        this.numberOfMonth = numberOfMonth;
    }

    public BigDecimal getLoanBalance() {
        return loanBalance;
    }

    public void setLoanBalance(BigDecimal loanBalance) {
        this.loanBalance = loanBalance;
    }

    public BigDecimal getCreditBody() {
        return creditBody;
    }

    public void setCreditBody(BigDecimal creditBody) {
        this.creditBody = creditBody;
    }

    public BigDecimal getPayOfPercent() {
        return payOfPercent;
    }

    public void setPayOfPercent(BigDecimal payOPercent) {
        this.payOfPercent = payOPercent;
    }

    public BigDecimal getPayForMonth() {
        return payForMonth;
    }

    public void setPayForMonth(BigDecimal payForMonth) {
        this.payForMonth = payForMonth;
    }

    @Override
    public String toString() {
        return "CreditCalcRow{" +
                "numberOfMonth=" + numberOfMonth +
                ", loanBalance=" + loanBalance +
                ", creditBody=" + creditBody +
                ", payOPercent=" + payOfPercent +
                ", payForMonth=" + payForMonth +
                '}';
    }
}
