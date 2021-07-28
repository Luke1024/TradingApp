package com.backend.app.service.account;

import com.backend.app.domain.dto.AccountDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.User;
import com.backend.app.domain.enums.AccountStatus;
import com.backend.app.mapper.AccountMapper;
import com.backend.app.repository.AccountRepository;
import com.backend.app.service.order.OrderService;
import com.backend.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AccountMapper accountMapper;

    private Logger logger = LoggerFactory.getLogger(AccountService.class);

    public Optional<Account> getAccount(String token,long id){
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isPresent()){
            if(accountOptional.get().getAccountStatus() != AccountStatus.DELETED){
                return Optional.of(accountOptional.get());
            }
        }
        return Optional.empty();
    }

    public List<Account> getOpenedAccountsByToken(String token){
        Optional<User> userOptional = userService.getUser(token);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            return user.getAccounts().stream()
                    .filter(account -> account.getAccountStatus()== AccountStatus.OPENED).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public Optional<Account> saveAccount(String token, AccountDto accountDto){
        Optional<User> userOptional = userService.getUser(token);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Account> accountOptional = accountMapper.mapToNewAccount(accountDto, user);
            if(accountOptional.isPresent()) {
                Account account = accountRepository.save(accountOptional.get());
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }

    public Optional<Account> updateAccount(String token, AccountDto accountDto){
        Optional<Account> accountOptional = accountMapper.mapToExistingAccount(accountDto);
        if(accountOptional.isPresent()) {
            Account account = accountRepository.save(accountOptional.get());
            return Optional.of(account);
        }
        return Optional.empty();
    }

    public boolean deleteAccount(String token, long id){
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isPresent()) {
            Account account = accountOptional.get();
            if (account.getAccountStatus() == AccountStatus.OPENED) {
                account.setAccountStatus(AccountStatus.ARCHIVED);
            }
            if (account.getAccountStatus() == AccountStatus.ARCHIVED) {
                account.setAccountStatus(AccountStatus.DELETED);
            }
            accountRepository.save(account);
            return true;
        } else {
            return false;
        }
    }
}
