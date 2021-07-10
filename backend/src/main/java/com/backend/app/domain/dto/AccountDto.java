package com.backend.app.domain.dto;

import java.util.List;

public class AccountDto {
    private long id;
    private String accountName;
    private int leverage;
    private double balance;
    private boolean created;
    private List<OrderDto> orders;

    public AccountDto() {
    }

    public AccountDto(long id, String accountName, int leverage, double balance, boolean created, List<OrderDto> orders) {
        this.id = id;
        this.accountName = accountName;
        this.leverage = leverage;
        this.balance = balance;
        this.created = created;
        this.orders = orders;
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

    public boolean isCreated() {
        return created;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }
}
