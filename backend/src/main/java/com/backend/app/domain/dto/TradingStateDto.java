package com.backend.app.domain.dto;

import java.util.List;

public class TradingStateDto {
    private String token;
    private List<AccountDto> accounts;

    public TradingStateDto() { }

    public TradingStateDto(String token, List<AccountDto> accounts) {
        this.token = token;
        this.accounts = accounts;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<AccountDto> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDto> accounts) {
        this.accounts = accounts;
    }
}
