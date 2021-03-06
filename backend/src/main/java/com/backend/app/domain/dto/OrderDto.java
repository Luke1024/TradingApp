package com.backend.app.domain.dto;

import com.backend.app.domain.enums.OrderState;

public class OrderDto {
    private long accountId;
    private long id;
    private String currency;
    private double lot;
    private int tpPips;
    private double tpVal;
    private int slPips;
    private double slVal;
    private double profit;
    private boolean longOrder;
    private OrderState orderState;

    public OrderDto() {
    }

    public OrderDto(long accountId, long id, String currency, double lot, int tpPips, double tpVal,
                    int slPips, double slVal, double profit, boolean longOrder, OrderState orderState) {
        this.accountId = accountId;
        this.id = id;
        this.currency = currency;
        this.lot = lot;
        this.tpPips = tpPips;
        this.tpVal = tpVal;
        this.slPips = slPips;
        this.slVal = slVal;
        this.profit = profit;
        this.longOrder = longOrder;
        this.orderState = orderState;
    }

    public long getAccountId() {
        return accountId;
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

    public boolean isLongOrder() {
        return longOrder;
    }

    public OrderState getOrderState() {
        return orderState;
    }
}
