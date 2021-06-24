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
}
