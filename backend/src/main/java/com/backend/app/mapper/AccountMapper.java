package com.backend.app.mapper;

import com.backend.app.domain.dto.AccountDto;
import com.backend.app.domain.dto.AccountInfoDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.User;
import com.backend.app.repository.AccountRepository;
import com.backend.app.service.account.AccountCorrectnessGuardService;
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

    @Autowired
    private AccountCorrectnessGuardService accountCorrectnessGuardService;

    public Optional<Account> mapToNewAccount(AccountDto accountDto, User user){
        AccountInfoDto accountInfoDto = accountCorrectnessGuardService.getInfo(accountDto);
        if(accountInfoDto.isStatus()) {
            return Optional.of(new Account(
                    accountDto.getAccountName(),
                    accountDto.getLeverage(),
                    accountDto.getBalance(),
                    user));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Account> mapToExistingAccount(AccountDto accountDto){
        Optional<Account> accountOptional = accountRepository.findById((accountDto.getId()));
        AccountInfoDto accountInfoDto = accountCorrectnessGuardService.getInfo(accountDto);
        if(accountOptional.isPresent() && accountInfoDto.isStatus()){
            return mapExistingAccount(accountOptional.get(),accountDto);
        } else {
            return Optional.empty();
        }
    }

    public AccountDto mapToExistingAccountDtoWithoutOrder(Account account){
        return new AccountDto(
                account.getId(),
                account.getAccountName(),
                account.getLeverage(),
                account.getBalance(),
                true,
                new ArrayList<>()
        );
    }

    private Optional<Account> mapExistingAccount(Account existingAccount, AccountDto accountDto){
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
                true,
                orderMapper.mapToOrderDtoList(account.getCurrencyOrders())
        );
    }
}
