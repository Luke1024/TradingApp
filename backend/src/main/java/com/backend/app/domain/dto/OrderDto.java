package com.backend.app.domain.dto;

import com.backend.app.domain.ShortLong;

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
    private ShortLong shortLong;
    private boolean created;

    public OrderDto() {
    }

    public OrderDto(long accountId, long id, String currency, double lot, int tpPips,
                    double tpVal, int slPips, double slVal, double profit, ShortLong shortLong, boolean created) {
        this.accountId = accountId;
        this.id = id;
        this.currency = currency;
        this.lot = lot;
        this.tpPips = tpPips;
        this.tpVal = tpVal;
        this.slPips = slPips;
        this.slVal = slVal;
        this.profit = profit;
        this.shortLong = shortLong;
        this.created = created;
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

    public ShortLong getShortLong() {
        return shortLong;
    }

    public boolean isCreated() {
        return created;
    }
}
