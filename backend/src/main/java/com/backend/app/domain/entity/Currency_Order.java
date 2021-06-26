package com.backend.app.domain.entity;

import com.backend.app.domain.State;

import javax.persistence.*;

@Entity
public class Currency_Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String currency;
    private int lot;
    private int tpPips;
    private int tpVal;
    private int slPips;
    private int slVal;
    private int profit;
    private State state;
    private String message;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    public Currency_Order() {
    }

    public Currency_Order(String currency, int lot, int tpPips, int tpVal, int slPips, int slVal,
                          int profit, State state, String message, Account account) {
        this.currency = currency;
        this.lot = lot;
        this.tpPips = tpPips;
        this.tpVal = tpVal;
        this.slPips = slPips;
        this.slVal = slVal;
        this.profit = profit;
        this.state = state;
        this.message = message;
        setAccount(account);
    }

    private void setAccount(Account account){
        if(account != null){
            account.getCurrencyOrders().add(this);
        } else if(this.account != null){
            this.account.getCurrencyOrders().remove(this);
        }
        this.account = account;
    }

    public long getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public int getLot() {
        return lot;
    }

    public int getTpPips() {
        return tpPips;
    }

    public int getTpVal() {
        return tpVal;
    }

    public int getSlPips() {
        return slPips;
    }

    public int getSlVal() {
        return slVal;
    }

    public int getProfit() {
        return profit;
    }

    public State getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public Account getAccount() {
        return account;
    }
}
