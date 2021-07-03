package com.backend.app.domain.dto;

public class AccountDtoMessage {
    private String name;
    private String leverage;
    private String balance;

    public AccountDtoMessage() {
    }

    public AccountDtoMessage(String name, String leverage, String balance) {
        this.name = name;
        this.leverage = leverage;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public String getLeverage() {
        return leverage;
    }

    public String getBalance() {
        return balance;
    }
}
