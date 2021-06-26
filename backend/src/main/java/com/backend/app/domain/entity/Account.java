package com.backend.app.domain.entity;

import com.backend.app.domain.State;

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
    private State state;
    private String message;

    @OneToMany(targetEntity = Currency_Order.class,
        mappedBy = "account",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY)
    @OrderColumn
    private List<Currency_Order> currencyOrders;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Account(String accountName, int leverage, double balance, State state,
                   String message, List<Currency_Order> currencyOrders, User user) {
        this.accountName = accountName;
        this.leverage = leverage;
        this.balance = balance;
        this.state = state;
        this.message = message;
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

    public State getState() {
        return state;
    }

    public String getMessage() {
        return message;
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

    public void setState(State state) {
        this.state = state;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
