package com.backend.app.domain.dto;

import com.backend.app.domain.State;

import java.util.List;

public class AccountDto {
    private long id;
    private String accountName;
    private int leverage;
    private double balance;
    private State state;
    private String message;
    private List<OrderDto> orders;

    public AccountDto() {
    }

    public AccountDto(long id, String accountName, int leverage, double balance,
                      State state, String message, List<OrderDto> orders) {
        this.id = id;
        this.accountName = accountName;
        this.leverage = leverage;
        this.balance = balance;
        this.state = state;
        this.message = message;
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

    public State getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }
}
