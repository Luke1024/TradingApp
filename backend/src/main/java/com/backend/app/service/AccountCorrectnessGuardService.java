package com.backend.app.service;

import com.backend.app.domain.dto.AccountDto;
import com.backend.app.domain.dto.AccountInfoDto;
import com.backend.app.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCorrectnessGuardService {

    @Autowired
    private AccountMessageService accountMessageService;

    private static int maxLeverage = 100;

    @Autowired
    private AccountRepository accountRepository;

    private Logger logger = LoggerFactory.getLogger(AccountCorrectnessGuardService.class);


    public AccountInfoDto getInfo(AccountDto accountDto){
        String nameInfo = analyzeName(accountDto);
        String leverageInfo = analyzeLeverage(accountDto);
        String balanceInfo = analyzeBalance(accountDto);
        if(isStringBiggerThanZero(nameInfo, leverageInfo, balanceInfo)){
            return new AccountInfoDto(nameInfo, leverageInfo, balanceInfo, false);
        } else return new AccountInfoDto(nameInfo, leverageInfo, balanceInfo, true);
    }

    private boolean isStringBiggerThanZero(String nameInfo, String leverageInfo, String balanceInfo){
        if(nameInfo.length()>0 || leverageInfo.length()>0 || balanceInfo.length()>0){
            return true;
        } else return false;
    }

    private String analyzeName(AccountDto accountDto){
        if(accountDto.getAccountName().length()>0){
            return "";
        } else {
            return accountMessageService.accountNameToShort;
        }
    }

    private String analyzeLeverage(AccountDto accountDto){
        if(accountDto.getLeverage()>maxLeverage){
            return accountMessageService.maxLeverage;
        } else return "";
    }

    private String analyzeBalance(AccountDto accountDto){
        if(accountDto.getBalance()<0){
            return accountMessageService.balanceOnMinus;
        } else return "";
    }
}
