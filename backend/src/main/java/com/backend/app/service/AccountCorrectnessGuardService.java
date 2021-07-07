package com.backend.app.service;

import com.backend.app.domain.State;
import com.backend.app.domain.dto.AccountDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountCorrectnessGuardService {

    @Autowired
    private AccountMessageService accountMessageService;

    @Autowired
    private AccountSettings accountSettings;

    @Autowired
    private AccountRepository accountRepository;

    private Logger logger = LoggerFactory.getLogger(AccountCorrectnessGuardService.class);

    public Optional<AccountDto> saveAccount(AccountDto accountDto){
        State state = accountDto.getState();
        if(state==State.CREATION){
            return allowCreatingAccount(accountDto);
        } else {
            logger.warn("Account to save must be in creation mode.");
            return Optional.empty();
        }
    }

    private Optional<AccountDto> allowCreatingAccount(AccountDto accountDto){
        analyzeNaming(accountDto);
        analyzeLeverage(accountDto);
        return Optional.of(modifiedDto);
    }

    private AccountDto analyzeNaming(AccountDto inputDto){
        if(inputDto.getAccountName().length()==0){
            inputDto.setNameMessage(accountMessageService.getToShortAccountName());
        } else {
            inputDto.setNameMessage("");
        }
        return inputDto;
    }

    private AccountDto analyzeLeverage(AccountDto accountDto){
        if(accountDto.getLeverage() > accountSettings.getMaxLeverage()) {
            accountDto.setLeverageMessage(accountMessageService.getToBigLeverage);
        } else if(accountDto.getLeverage() < 1){
            accountDto.setLeverageMessage(accountMessageService.getToSmallLeverage);
        } else {
            accountDto.setLeverageMessage("");
        }
        return accountDto;
    }

    public Optional<AccountDto> updateAccount(AccountDto accountDto){
        Optional<Account> realAccount = accountRepository.findById(accountDto.getId());
        if(realAccount.isPresent()){
            return allowUpdatingAccount(realAccount.get(), accountDto);
        } else {
            logger.warn("Allow to update don't exist");
            return Optional.empty();
        }
    }

    private Optional<AccountDto> allowUpdatingAccount(Account accountToUpdate, AccountDto accountDto){
        AccountDto
        AccountDto dtoWithStateFiltered = analyzeIfOperationIsAllowable(accountToUpdate, accountDto);

    }

    private AccountDto analyzeIfOperationIsAllowable(Account accountToUpdate, AccountDto accountDto){
        State accountState = accountToUpdate.getState();
        State dtoState = accountDto.getState();


    }
}
