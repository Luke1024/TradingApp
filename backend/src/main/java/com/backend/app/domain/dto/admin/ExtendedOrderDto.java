package com.backend.app.domain.dto.admin;

import com.backend.app.domain.enums.OrderState;
import com.backend.app.domain.enums.ShortLong;

import java.time.LocalDateTime;

public class ExtendedOrderDto {
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
    private LocalDateTime creationDate;

    public ExtendedOrderDto() {
    }

    public ExtendedOrderDto(long id, String currency, double lot, int tpPips, double tpVal, int slPips, double slVal,
                            double profit, ShortLong shortLong, OrderState orderState, LocalDateTime creationDate) {
        this.id = id;
        this.currency = currency;
        this.lot = lot;
        this.tpPips = tpPips;
        this.tpVal = tpVal;
        this.slPips = slPips;
        this.slVal = slVal;
        this.profit = profit;
        this.shortLong = shortLong;
        this.orderState = orderState;
        this.creationDate = creationDate;
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

    public ShortLong getShortLong() {
        return shortLong;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
