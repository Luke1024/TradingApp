package com.backend.app.domain.entity;

import com.backend.app.domain.enums.AccountStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private List<CurrencyOrder> currencyOrders = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "APPUSER_ID")
    private AppUser appUser;
    private AccountStatus accountStatus;
    private LocalDateTime creationDate;

    public Account() {
    }

    public Account(String accountName, int leverage, double balance, AppUser appUser, AccountStatus accountStatus) {
        this.accountName = accountName;
        this.leverage = leverage;
        this.balance = balance;
        setAppUser(appUser);
        this.accountStatus = accountStatus;
        this.creationDate = LocalDateTime.now();
    }

    public Account(long id, String accountName, int leverage,
                   double balance, List<CurrencyOrder> currencyOrders, AppUser appUser, AccountStatus accountStatus) {
        this.id = id;
        this.accountName = accountName;
        this.leverage = leverage;
        this.balance = balance;
        this.currencyOrders = currencyOrders;
        this.appUser = appUser;
        this.accountStatus = accountStatus;
    }

    public void setAppUser(AppUser appUser){
        if(appUser != null){
            appUser.getAccounts().add(this);
        } else if(this.appUser != null){
            this.appUser.getAccounts().remove(this);
        }
        this.appUser = appUser;
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

    public AppUser getAppUser() {
        return appUser;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountName='" + accountName + '\'' +
                ", leverage=" + leverage +
                ", balance=" + balance +
                ", user=" + appUser +
                '}';
    }
}
