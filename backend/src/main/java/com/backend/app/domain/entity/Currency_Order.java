package com.backend.app.domain.entity;

import com.backend.app.domain.State;

import javax.persistence.*;

@Entity
public class Currency_Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String currency;
    private double lot;
    private int tpPips;
    private double tpVal;
    private int slPips;
    private double slVal;
    private double profit;
    private State state;

    @ManyToOne()
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    public Currency_Order() {
    }

    public Currency_Order(String currency, double lot, int tpPips, double tpVal, int slPips, double slVal,
                          double profit, State state, Account account) {
        this.currency = currency;
        this.lot = lot;
        this.tpPips = tpPips;
        this.tpVal = tpVal;
        this.slPips = slPips;
        this.slVal = slVal;
        this.profit = profit;
        this.state = state;
        setAccount(account);
    }

    public void setAccount(Account account){
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

    public double getLot() {
        return lot;
    }

    public int getTpPips() {
        return tpPips;
    }

    public double getTpVal() {
        return tpVal;
    }

    public int getSlPips() {
        return slPips;
    }

    public double getSlVal() {
        return slVal;
    }

    public double getProfit() {
        return profit;
    }

    public State getState() {
        return state;
    }

    public void setTpPips(int tpPips) {
        this.tpPips = tpPips;
    }

    public void setTpVal(double tpVal) {
        this.tpVal = tpVal;
    }

    public void setSlPips(int slPips) {
        this.slPips = slPips;
    }

    public void setSlVal(double slVal) {
        this.slVal = slVal;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setLot(double lot) {
        this.lot = lot;
    }
}
