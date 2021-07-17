package com.backend.app.domain.entity;

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

    @OneToMany(targetEntity = Currency_Order.class,
        mappedBy = "account",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER)
    @OrderColumn
    private List<Currency_Order> currencyOrders;

    @ManyToOne()
    @JoinColumn(name = "USER_ID")
    private User user;

    public Account() {
    }

    public Account(String accountName, int leverage, double balance, List<Currency_Order> currencyOrders, User user) {
        this.accountName = accountName;
        this.leverage = leverage;
        this.balance = balance;
        this.currencyOrders = new ArrayList<>();
        setUser(user);
    }

    public void setUser(User user){
        if(user != null){
            user.getAccounts().add(this);
        } else if(this.user != null){
            this.user.getAccounts().remove(this);
        }
        this.user = user;
    }

    public void addOrder(Currency_Order currency_order){
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

    public List<Currency_Order> getCurrencyOrders() {
        return currencyOrders;
    }

    public User getUser() {
        return user;
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
}
