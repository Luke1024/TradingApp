package com.backend.app.domain.entity;

import com.backend.app.domain.enums.OrderState;
import com.backend.app.domain.enums.ShortLong;

import javax.persistence.*;

@NamedNativeQuery(
        name = "CurrencyOrder.findByIdArchivedFalse",
        query = "SELECT * FROM currency_order WHERE id=:ID AND archived=false;",
        resultClass = CurrencyOrder.class
)

@Entity
public class CurrencyOrder {

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
    private ShortLong shortLong;
    private OrderState orderState;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    public CurrencyOrder() {
    }

    public CurrencyOrder(String currency, double lot, int tpPips, double tpVal, int slPips,
                         double slVal, ShortLong shortLong, OrderState orderState, Account account) {
        this.currency = currency;
        this.lot = lot;
        this.tpPips = tpPips;
        this.tpVal = tpVal;
        this.slPips = slPips;
        this.slVal = slVal;
        this.profit = 0;
        this.orderState = orderState;
        this.shortLong = shortLong;
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

    public ShortLong getShortLong() {
        return shortLong;
    }

    public double getProfit() {
        return profit;
    }

    public Account getAccount() {
        return account;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
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

    public void setLot(double lot) {
        this.lot = lot;
    }

    @Override
    public String toString() {
        return "CurrencyOrder{" +
                "currency='" + currency + '\'' +
                ", lot=" + lot +
                ", tpPips=" + tpPips +
                ", tpVal=" + tpVal +
                ", slPips=" + slPips +
                ", slVal=" + slVal +
                ", profit=" + profit +
                ", shortLong=" + shortLong +
                ", orderState=" + orderState +
                '}';
    }
}
