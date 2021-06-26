package com.backend.app.service;

import com.backend.app.controler.AccountController;
import com.backend.app.domain.dto.AccountDto;
import com.backend.app.domain.dto.TradingStateDto;
import com.backend.app.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public TradingStateDto saveAccount(String token, AccountDto accountDto){
        return new TradingStateDto();
    }

    public TradingStateDto updateAccount(String token, AccountDto accountDto){
        return new TradingStateDto();
    }

    public TradingStateDto deleteAccount(String token, long id){
        return new TradingStateDto();
    }
}
