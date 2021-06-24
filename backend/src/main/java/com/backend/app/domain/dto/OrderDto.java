package com.backend.app.domain.dto;

import com.backend.app.domain.State;

public class OrderDto {
    private long id;
    private String currency;
    private double lot;
    private int tpPips;
    private double tpVal;
    private int slPips;
    private double slVal;
    private double profit;
    private State state;
    private String message;

    public OrderDto() {
    }

    public OrderDto(long id, String currency, double lot, int tpPips, double tpVal,
                    int slPips, double slVal, double profit, State state, String message) {
        this.id = id;
        this.currency = currency;
        this.lot = lot;
        this.tpPips = tpPips;
        this.tpVal = tpVal;
        this.slPips = slPips;
        this.slVal = slVal;
        this.profit = profit;
        this.state = state;
        this.message = message;
    }
}
