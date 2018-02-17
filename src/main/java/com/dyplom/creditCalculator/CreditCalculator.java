package com.dyplom.creditCalculator;

import com.dyplom.entity.Payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class CreditCalculator {

    //output data
    private BigDecimal creditBody;
    private List<CreditCalcRow> rows = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();
    private BigDecimal allPays;
    private BigDecimal payForPercent;

    public CreditCalculator(){

    }

    public CreditCalculator(BigDecimal sum, float percentage, int countOfMonth){
        calculate(sum, percentage, countOfMonth);
    }

    public BigDecimal getCreditBody() {
        return creditBody;
    }

    public void setCreditBody(BigDecimal creditBody) {
        this.creditBody = creditBody;
    }

    public List<CreditCalcRow> getRows() {
        return rows;
    }

    public void setRows(List<CreditCalcRow> rows) {
        this.rows = rows;
    }

    public BigDecimal getAllPays() {
        return allPays;
    }

    public void setAllPays(BigDecimal allPays) {
        this.allPays = allPays;
    }

    public BigDecimal getPayForPercent() {
        return payForPercent;
    }

    public void setPayForPercent(BigDecimal payForPercent) {
        this.payForPercent = payForPercent;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }


    void calculate(BigDecimal sum, float percentage, int countOfMonth){

        creditBody = sum.divide(new BigDecimal(countOfMonth), 2, RoundingMode.HALF_UP); //ежемесячный платеж по основному долгу

        BigDecimal payForMonth; //ежемесячный платеж по основному долгу + проценты || creditBody + (sum - (creditBody * i)) * percentage / 12;

        allPays = new BigDecimal(0);

        payForPercent = new BigDecimal(0);


        BigDecimal loanBalance = sum; //остаток кредита
        BigDecimal payOfPercent;


        for (int i = 0; i < countOfMonth; i++) {

            payForMonth = creditBody.add(sum.subtract(creditBody.multiply(new BigDecimal(i))).multiply(new BigDecimal(percentage/100)).divide(new BigDecimal(12), 2, RoundingMode.HALF_UP));

            if (i == 0)
                loanBalance = sum;
            else
                loanBalance = loanBalance.subtract(creditBody);

            allPays = allPays.add(payForMonth);

            payOfPercent = payForMonth.subtract(creditBody);

            rows.add(new CreditCalcRow(i, loanBalance, creditBody, payOfPercent, payForMonth));



            Payment p = new Payment();
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.add(Calendar.MONTH, i);

            p.setDateOfPayment(calendar.getTime());
            p.setStateOfPayment("не оплачено");
            p.setSumOfPayment(payForMonth);
            payments.add(p);
        }

        payForPercent = allPays.subtract(sum);
    }
}
