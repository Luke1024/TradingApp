package com.backend.app.domain.dto;

import com.backend.app.domain.State;

import java.util.List;

public class AccountDto {
    private long id;
    private String accountName;
    private String nameMessage;
    private int leverage;
    private String leverageMessage;
    private double balance;
    private State state;
    private List<OrderDto> orders;

    public AccountDto() {
    }

    public AccountDto(long id, String accountName, String nameMessage, int leverage,
                      String leverageMessage, double balance, State state, List<OrderDto> orders) {
        this.id = id;
        this.accountName = accountName;
        this.nameMessage = nameMessage;
        this.leverage = leverage;
        this.leverageMessage = leverageMessage;
        this.balance = balance;
        this.state = state;
        this.orders = orders;
    }

    public long getId() {
        return id;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getNameMessage() {
        return nameMessage;
    }

    public int getLeverage() {
        return leverage;
    }

    public String getLeverageMessage() {
        return leverageMessage;
    }

    public double getBalance() {
        return balance;
    }

    public State getState() {
        return state;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setNameMessage(String nameMessage) {
        this.nameMessage = nameMessage;
    }

    public void setLeverage(int leverage) {
        this.leverage = leverage;
    }

    public void setLeverageMessage(String leverageMessage) {
        this.leverageMessage = leverageMessage;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setOrders(List<OrderDto> orders) {
        this.orders = orders;
    }
}
