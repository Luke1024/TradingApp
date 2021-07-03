package com.backend.app.domain.dto;

public class OrderDtoMessage {
    private String lot;
    private String tpPips;
    private String slPips;

    public OrderDtoMessage() {
    }

    public OrderDtoMessage(String lot, String tpPips, String slPips) {
        this.lot = lot;
        this.tpPips = tpPips;
        this.slPips = slPips;
    }

    public String getLot() {
        return lot;
    }

    public String getTpPips() {
        return tpPips;
    }

    public String getSlPips() {
        return slPips;
    }
}
