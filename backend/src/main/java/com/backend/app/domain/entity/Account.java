package com.backend.app.domain.entity;

import com.backend.app.domain.enums.AccountStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String accountName;
    private int leverage;
    private double balance;

    @OneToMany(targetEntity = CurrencyOrder.class,
        mappedBy = "account",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER)
    @OrderColumn
    private List<CurrencyOrder> currencyOrders;

    @ManyToOne()
    @JoinColumn(name = "USER_ID")
    private User user;
    private AccountStatus accountStatus;

    public Account() {
    }

    public Account(String accountName, int leverage, double balance, User user, AccountStatus accountStatus) {
        this.accountName = accountName;
        this.leverage = leverage;
        this.balance = balance;
        this.currencyOrders = new ArrayList<>();
        setUser(user);
        this.accountStatus = accountStatus;
    }

    public Account(long id, String accountName, int leverage,
                   double balance, List<CurrencyOrder> currencyOrders, User user, AccountStatus accountStatus) {
        this.id = id;
        this.accountName = accountName;
        this.leverage = leverage;
        this.balance = balance;
        this.currencyOrders = currencyOrders;
        this.user = user;
        this.accountStatus = accountStatus;
    }

    public void setUser(User user){
        if(user != null){
            user.getAccounts().add(this);
        } else if(this.user != null){
            this.user.getAccounts().remove(this);
        }
        this.user = user;
    }

    public void addOrder(CurrencyOrder currency_order){
        if(currency_order != null){
            currency_order.setAccount(this);
        }
    }

    public long getId() {
        return id;
    }

    public String getAccountName() {
        return accountName;
    }

    public int getLeverage() {
        return leverage;
    }

    public double getBalance() {
        return balance;
    }

    public List<CurrencyOrder> getCurrencyOrders() {
        return currencyOrders;
    }

    public User getUser() {
        return user;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setLeverage(int leverage) {
        this.leverage = leverage;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountName='" + accountName + '\'' +
                ", leverage=" + leverage +
                ", balance=" + balance +
                ", user=" + user +
                '}';
    }
}
