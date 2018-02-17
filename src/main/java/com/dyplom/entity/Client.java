package com.dyplom.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    @NotEmpty(message = "*Пожалуйста, введите Ваше Ф.И.О.")
    private String name;
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "*Пожалуйста, введите Вашу дату рождения")
    private Date dob;
    @Column(name = "citizen")
    @NotEmpty(message = "*Пожалуйста, введите Ваше гражданство")
    private String citizen;
    @Column(name = "passport_id")
    @Length(min = 14, message = "*Идентификационный номер паспорта длинной в 14 символов")
    @NotEmpty(message = "*Пожалуйста, введите Ваш идентификационный номер паспорта")
    private String passportId;
    @Column(name = "passport_department")
    @NotEmpty(message = "*Пожалуйста, введите название органа, которым был выдан паспорт")
    private String passportDepartment;
    @Column(name = "adress")
    @NotEmpty(message = "*Пожалуйста, введите Ваш адресс места жительства")
    private String adress;
    @Column(name = "phone")
    @Length(min = 17, message = "*пожалуйста введите номер телефона по форме +375-ХХ-ХХХ-ХХ-ХХ")
    @NotEmpty(message = "*Пожалуйста, введите Ваш номер мобилього телефона")
    private String phone;
    @Column(name = "email")
    @Email(message = "*Пожалуйста, введите корректный адрес электронной почты")
    @NotEmpty(message = "*Пожалуйста, введите Ваш email")
    private String email;
    @Column(name = "income")
    @NotNull(message = "*Пожалуйста, введите размер Ваших доходов")
    private BigDecimal income;
    @Column(name = "expenditure")
    @NotNull(message = "*Пожалуйста, введите размер Ваших расходов")
    private BigDecimal expenditure;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private List<Contract> contract;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private List<TestResult> testResultsList;

    public Client() {
    }

    public Client(String name, Date dob, String citizen, String passportId, String passportDepartment,
                  String adress, String phone, String email, BigDecimal income, BigDecimal expenditure,
                  List<Contract> contract, List<TestResult> testResultsList) {
        this.name = name;
        this.dob = dob;
        this.citizen = citizen;
        this.passportId = passportId;
        this.passportDepartment = passportDepartment;
        this.adress = adress;
        this.phone = phone;
        this.email = email;
        this.income = income;
        this.expenditure = expenditure;
        this.contract = contract;
        this.testResultsList = testResultsList;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getCitizen() {
        return citizen;
    }

    public void setCitizen(String citizen) {
        this.citizen = citizen;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public String getPassportDepartment() {
        return passportDepartment;
    }

    public void setPassportDepartment(String passportDepartment) {
        this.passportDepartment = passportDepartment;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(BigDecimal expenditure) {
        this.expenditure = expenditure;
    }

    public List<Contract> getContract() {
        return contract;
    }

    public void setContract(List<Contract> contract) {
        this.contract = contract;
    }

    public List<TestResult> getTestResultsList() {
        return testResultsList;
    }

    public void setTestResultsList(List<TestResult> testResultsList) {
        this.testResultsList = testResultsList;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", citizen='" + citizen + '\'' +
                ", passportId='" + passportId + '\'' +
                ", passportDepartment='" + passportDepartment + '\'' +
                ", adress='" + adress + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", contract=" + contract +
                ", testResultsList=" + testResultsList +
                '}';
    }
}

