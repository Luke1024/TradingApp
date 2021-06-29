package com.backend.app.service;

import com.backend.app.domain.State;
import com.backend.app.domain.dto.AccountDto;
import com.backend.app.domain.dto.TradingStateDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.User;
import com.backend.app.mapper.AccountMapper;
import com.backend.app.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TradingStateService tradingStateService;

    private Logger logger = LoggerFactory.getLogger(AccountService.class);

    public Optional<Account> getAccount(long id){
        return accountRepository.findById(id);
    }

    public TradingStateDto saveAccount(String token, AccountDto accountDto){
        if(accountDtoNullCheck(accountDto)) {
            if (checkStateForSaving(accountDto)) {
                Optional<User> userOptional = userService.getUser(token);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    user.addAcount(accountMapper.mapToNewAccount(accountDto, user));
                }
            }
        }
        return tradingStateService.getTradingState(token);
    }

    private boolean accountDtoNullCheck(AccountDto accountDto){
        if(accountDto != null){
            return true;
        } else {
            logger.warn("AccountDto is null.");
            return false;
        }
    }

    private boolean checkStateForSaving(AccountDto accountDto){
        if(accountDto.getState()==State.CREATION){
            return true;
        } else {
            logger.warn("Account must be in creation mode for saving.");
            return false;
        }
    }

    public TradingStateDto updateAccount(String token, AccountDto accountDto){
        if(accountDtoNullCheck(accountDto)){
            Optional<Account> accountOptional = getAccount(accountDto);
            if(accountOptional.isPresent()){
                mapAndUpdateAccount(accountOptional.get(),accountDto);
            } else {
                logger.warn("Account not found.");
            }
        }
        return tradingStateService.getTradingState(token);
    }

    private Optional<Account> getAccount(AccountDto accountDto){
        return accountRepository.findById(accountDto.getId());
    }

    private void mapAndUpdateAccount(Account accountFromDatabase, AccountDto accountDto){
        accountFromDatabase.setAccountName(accountDto.getAccountName());
        accountFromDatabase.setLeverage(accountDto.getLeverage());
        accountFromDatabase.setState(accountDto.getState());
        accountFromDatabase.setMessage(accountDto.getMessage());
        accountRepository.save(accountFromDatabase);
    }

    public TradingStateDto deleteAccount(String token, long id){
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isPresent()){
            accountRepository.deleteById(id);
        } else {
            logger.warn("Account to delete not found.");
        }
        return tradingStateService.getTradingState(token);
    }
}
