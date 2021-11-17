package com.backend.app.mapper;

import com.backend.app.domain.dto.AccountDto;
import com.backend.app.domain.dto.AccountInfoDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.AppUser;
import com.backend.app.domain.enums.AccountStatus;
import com.backend.app.repository.AccountRepository;
import com.backend.app.service.account.AccountCorrectnessGuardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AccountMapper {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountCorrectnessGuardService accountCorrectnessGuardService;

    public List<AccountDto> mapToExistingAccountDtoList(List<Account> accounts){
        return accounts.stream().map(account -> mapToExistingAccountDto(account)).collect(Collectors.toList());
    }

    public Optional<Account> mapToNewAccount(AccountDto accountDto, AppUser appUser){
        AccountInfoDto accountInfoDto = accountCorrectnessGuardService.getInfo(accountDto);
        if(accountInfoDto.isStatus()) {
            return Optional.of(new Account(
                    accountDto.getAccountName(),
                    accountDto.getLeverage(),
                    accountDto.getBalance(),
                    appUser,
                    AccountStatus.OPENED));
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

    public AccountDto mapToExistingAccountDto(Account account){
        return new AccountDto(
                account.getId(),
                account.getAccountName(),
                account.getLeverage(),
                account.getBalance(),
                true);
    }

    private Optional<Account> mapExistingAccount(Account existingAccount, AccountDto accountDto){
        existingAccount.setAccountName(accountDto.getAccountName());
        existingAccount.setLeverage(accountDto.getLeverage());
        return Optional.of(existingAccount);
    }
}
