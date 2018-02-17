package com.dyplom.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import javax.persistence.Id;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "bank_worker")
public class BankWorker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    @NotEmpty(message = "*Пожалуйста, введите Ваше Ф.И.О.")
    private String name;
    @Column(name = "password")
    @Length(min = 5, message = "*Ваш пароль должен быть не менее 5-ти символов")
    @NotEmpty(message = "*Пожалуйста, введите Ваш пароль")
    @Transient
    private String password;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade =
                    {
                            CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.REFRESH,
                            CascadeType.PERSIST
                    },
            targetEntity = Role.class)
    @JoinTable(name = "bank_worker_has_role", joinColumns = @JoinColumn(name = "bank_worker_id", nullable = false,
            updatable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false,
                    updatable = false), foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Set<Role> role;
    @Column(name = "email")
    @Email(message = "*Пожалуйста, введите корректный адрес электронной почты")
    @NotEmpty(message = "*Пожалуйста, введите Ваш email")
    private String email;
    @Column(name = "phone")
    @NotEmpty(message = "*Пожалуйста, введите Ваш мобильный телефон")
    private String phone;
    @Column(name = "active")
    private int active;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bankWorker")
    private List<Contract> contractList;

    public BankWorker() {
    }

    public BankWorker(String name, String password, Set<Role> role, String email, String phone, int active, Bank bank, List<Contract> contractList) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.active = active;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
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
        return "BankWorker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", active=" + active +
                ", bank=" + bank +
                ", contractList=" + contractList +
                '}';
    }
}
