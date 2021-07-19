package com.backend.app.domain.dto;

public class AccountResponseDto {
    private boolean status;
    private AccountDto accountDto;

    public AccountResponseDto() {
    }

    public AccountResponseDto(boolean status, AccountDto accountDto) {
        this.status = status;
        this.accountDto = accountDto;
    }

    public boolean isStatus() {
        return status;
    }

    public AccountDto getAccountDto() {
        return accountDto;
    }
}
