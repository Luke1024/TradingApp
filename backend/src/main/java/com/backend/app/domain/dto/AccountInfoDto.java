package com.backend.app.domain.dto;

public class AccountInfoDto {
    private String nameInfo;
    private String leverageInfo;
    private String balanceInfo;
    private boolean status;

    public AccountInfoDto() {
    }

    public AccountInfoDto(String nameInfo, String leverageInfo, String balanceInfo, boolean status) {
        this.nameInfo = nameInfo;
        this.leverageInfo = leverageInfo;
        this.balanceInfo = balanceInfo;
        this.status = status;
    }

    public String getNameInfo() {
        return nameInfo;
    }

    public String getLeverageInfo() {
        return leverageInfo;
    }

    public String getBalanceInfo() {
        return balanceInfo;
    }

    public boolean isStatus() {
        return status;
    }
}
