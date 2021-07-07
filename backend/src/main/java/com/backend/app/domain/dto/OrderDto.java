package com.backend.app.domain.dto;

import com.backend.app.domain.State;

public class OrderDto {
    private long id;
    private String currency;
    private double lot;
    private String lotMessage;
    private int tpPips;
    private String tpPipsMessage;
    private double tpVal;
    private int slPips;
    private String slPipsMessage;
    private double slVal;
    private double profit;
    private State state;

    public OrderDto() {
    }

    public OrderDto(String currency, double lot, String lotMessage, int tpPips, String tpPipsMessage, double tpVal,
                    int slPips, String slPipsMessage, double slVal, double profit, State state) {
        this.currency = currency;
        this.lot = lot;
        this.lotMessage = lotMessage;
        this.tpPips = tpPips;
        this.tpPipsMessage = tpPipsMessage;
        this.tpVal = tpVal;
        this.slPips = slPips;
        this.slPipsMessage = slPipsMessage;
        this.slVal = slVal;
        this.profit = profit;
        this.state = state;
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

    public String getLotMessage() {
        return lotMessage;
    }

    public int getTpPips() {
        return tpPips;
    }

    public String getTpPipsMessage() {
        return tpPipsMessage;
    }

    public double getTpVal() {
        return tpVal;
    }

    public int getSlPips() {
        return slPips;
    }

    public String getSlPipsMessage() {
        return slPipsMessage;
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
}
