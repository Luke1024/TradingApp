package com.backend.app.domain.dto;

public class OrderInfoDto {
    private String lotInfo;
    private String tpPipsInfo;
    private int tpVal;
    private String slPipsInfo;
    private int slVal;
    private boolean status;

    public OrderInfoDto() {
    }

    public OrderInfoDto(String lotInfo, String tpPipsInfo, int tpVal,
                        String slPipsInfo, int slVal, boolean status) {
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

    public int getTpVal() {
        return tpVal;
    }

    public String getSlPipsInfo() {
        return slPipsInfo;
    }

    public int getSlVal() {
        return slVal;
    }

    public boolean isStatus() {
        return status;
    }
}
