package com.backend.app.mapper;

import com.backend.app.domain.dto.AccountDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.User;
import com.backend.app.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class AccountMapper {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AccountRepository accountRepository;

    public Account mapToNewAccount(AccountDto accountDto, User user){
        return new Account(
                accountDto.getAccountName(),
                accountDto.getLeverage(),
                accountDto.getBalance(),
                new ArrayList<>(),
                user
        );
    }

    public Optional<Account> mapToExistingAccount(AccountDto accountDto){
        Optional<Account> accountOptional = accountRepository.findById((accountDto.getId()));
        if(accountOptional.isPresent()){
            return mapAccount(accountOptional.get(),accountDto);
        } else {
            return Optional.empty();
        }
    }

    public AccountDto mapToAccountDtoWithoutOrder(Account account){
        return new AccountDto(
                account.getId(),
                account.getAccountName(),
                account.getLeverage(),
                account.getBalance(),
                new ArrayList<>()
        );
    }

    private Optional<Account> mapAccount(Account existingAccount, AccountDto accountDto){
        existingAccount.setAccountName(accountDto.getAccountName());
        existingAccount.setLeverage(accountDto.getLeverage());
        return Optional.of(existingAccount);
    }

    public AccountDto mapToAccountDtoWithOrders(Account account){
        return new AccountDto(
                account.getId(),
                account.getAccountName(),
                account.getLeverage(),
                account.getBalance(),
                orderMapper.mapToOrderDtoList(account.getCurrencyOrders())
        );
    }
}
