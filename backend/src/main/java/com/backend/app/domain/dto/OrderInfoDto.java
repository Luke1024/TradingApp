package com.backend.app.domain.dto;

public class OrderInfoDto {
    private String lotInfo;
    private String tpPipsInfo;
    private double tpVal;
    private String slPipsInfo;
    private double slVal;
    private boolean status;

    public OrderInfoDto() {
    }

    public OrderInfoDto(boolean status) {
        this.lotInfo = "";
        this.tpPipsInfo = "";
        this.tpVal = 0;
        this.slPipsInfo = "";
        this.slVal = 0;
        this.status = status;
    }

    public OrderInfoDto(String lotInfo, String tpPipsInfo, double tpVal,
                        String slPipsInfo, double slVal, boolean status) {
        this.lotInfo = lotInfo;
        this.tpPipsInfo = tpPipsInfo;
        this.tpVal = tpVal;
        this.slPipsInfo = slPipsInfo;
        this.slVal = slVal;
        this.status = status;
    }

    public String getLotInfo() {
        return lotInfo;
    }

    public String getTpPipsInfo() {
        return tpPipsInfo;
    }

    public double getTpVal() {
        return tpVal;
    }

    public String getSlPipsInfo() {
        return slPipsInfo;
    }

    public double getSlVal() {
        return slVal;
    }

    public boolean isStatus() {
        return status;
    }
}
