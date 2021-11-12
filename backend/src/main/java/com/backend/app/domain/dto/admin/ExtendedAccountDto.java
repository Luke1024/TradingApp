package com.backend.app.domain.dto.admin;

import com.backend.app.domain.enums.AccountStatus;

import java.time.LocalDateTime;

public class ExtendedAccountDto {
    private long id;
    private String accountName;
    private int leverage;
    private double balance;
    private AccountStatus accountStatus;
    private LocalDateTime creationDate;
    private int ordersCount;

    public ExtendedAccountDto() {
    }

    public ExtendedAccountDto(long id, String accountName, int leverage, double balance,
                              AccountStatus accountStatus, LocalDateTime creationDate, int ordersCount) {
        this.id = id;
        this.accountName = accountName;
        this.leverage = leverage;
        this.balance = balance;
        this.accountStatus = accountStatus;
        this.creationDate = creationDate;
        this.ordersCount = ordersCount;
    }

    public long getId() {
        return id;
    }

    public String getAccountName() {
        return accountName;
    }

    public int getLeverage() {
        return leverage;
    }

    public double getBalance() {
        return balance;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public int getOrdersCount() {
        return ordersCount;
    }
}
