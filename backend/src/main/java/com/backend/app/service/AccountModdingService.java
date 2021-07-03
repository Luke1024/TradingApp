package com.backend.app.service;

import com.backend.app.domain.State;
import com.backend.app.domain.dto.AccountDto;
import com.backend.app.domain.entity.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountModdingService {

    public Account mod(Account accountFromDatabase, AccountDto accountDto){
        State dtoState = accountDto.getState();
        State accountState = accountFromDatabase.getState();

        if(dtoState==State.EDIT || dtoState==State.OPEN){
            return mapSettingsAvailableInEditAndOpenMode(accountFromDatabase, accountDto);
        } else if(dtoState==State.CREATION && accountState==State.CREATION) {
            return mapSettingsAvailableInCreationMode(accountFromDatabase, accountDto);
        }
        return accountFromDatabase;
    }

    private Account mapSettingsAvailableInEditAndOpenMode(Account accountFromDatabase, AccountDto accountDto){
        accountFromDatabase.setAccountName(accountDto.getAccountName());
        accountFromDatabase.setLeverage(accountDto.getLeverage());
        accountFromDatabase.setState(accountDto.getState());
        return accountFromDatabase;
    }

    private Account mapSettingsAvailableInCreationMode(Account accountFromDatabase, AccountDto accountDto){
        accountFromDatabase.setAccountName(accountDto.getAccountName());
        accountFromDatabase.setLeverage(accountDto.getLeverage());
        accountFromDatabase.setBalance(accountDto.getBalance());
        accountFromDatabase.setState(accountDto.getState());
        return accountFromDatabase;
    }
}
