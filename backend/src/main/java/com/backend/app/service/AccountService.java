package com.backend.app.service;

import com.backend.app.domain.dto.AccountDto;
import com.backend.app.domain.dto.AccountInfoDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.User;
import com.backend.app.mapper.AccountMapper;
import com.backend.app.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TradingStateService tradingStateService;

    @Autowired
    private AccountGateway accountGateway;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountCorrectnessGuardService accountCorrectnessGuard;

    private Logger logger = LoggerFactory.getLogger(AccountService.class);

    public Optional<Account> getAccount(String token,long id){
        return accountRepository.findById(id);
    }

    public Optional<Account> saveAccount(String token, AccountDto accountDto){
        Optional<User> userOptional = userService.getUser(token);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            Account account = accountRepository.save(accountMapper.mapToNewAccount(accountDto, user));
            if (account != null) {
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }

    public Optional<Account> updateAccount(String token, AccountDto accountDto){
        Optional<Account> accountOptional = accountMapper.mapToExistingAccount(accountDto);
        if(accountOptional.isPresent()) {
            Account account = accountRepository.save(accountOptional.get());
            if(account != null){
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }

    public boolean deleteAccount(String token, long id){
        accountRepository.deleteById(id);
        return true;
    }
}
