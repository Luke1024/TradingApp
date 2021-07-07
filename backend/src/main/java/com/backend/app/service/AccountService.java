package com.backend.app.service;

import com.backend.app.domain.dto.AccountDto;
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

    public Optional<Account> getAccount(long id){
        return accountRepository.findById(id);
    }

    public List<AccountDto> saveAccount(String token, AccountDto accountDto){
        if(accountDtoNullCheck(accountDto)) {
            Optional<User> userOptional = userService.getUser(token);
            if(userOptional.isPresent()) {
                User user = userOptional.get();
                Optional<AccountDto> accountDtoOptional = accountCorrectnessGuard.saveAccount(accountDto);
                accountRepository.save(accountMapper.mapToNewAccount(, user));
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

    public List<AccountDto> updateAccount(String token, AccountDto accountDto){
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
        //think about implementation of correctness guard
        AccountDto accountDtoCorrected = accountCorrectnessGuard.updateAccount(accountFromDatabase, accountDto);

        accountRepository.save(accountMapper.mapToExistAccount(accountDtoCorrected));
    }

    public List<AccountDto> deleteAccount(String token, long id){
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isPresent()){
            accountRepository.deleteById(id);
        } else {
            logger.warn("Account to delete not found.");
        }
        return tradingStateService.getTradingState(token);
    }
}
