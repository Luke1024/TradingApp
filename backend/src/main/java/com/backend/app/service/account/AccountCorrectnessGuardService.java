package com.backend.app.service.account;

import com.backend.app.domain.dto.AccountDto;
import com.backend.app.domain.dto.AccountInfoDto;
import com.backend.app.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCorrectnessGuardService {

    private static int leverage = 100;

    @Autowired
    private AccountRepository accountRepository;

    public static final String accountNameToShort = "Account name to short";
    public static final String maxLeverage = "Leverage must be under 100";
    public static final String balanceOnMinus = "Balance on minus";

    public AccountInfoDto getInfo(AccountDto accountDto){
        String nameInfo = analyzeName(accountDto);
        String leverageInfo = analyzeLeverage(accountDto);
        String balanceInfo = analyzeBalance(accountDto);
        if(isStringBiggerThanZero(nameInfo, leverageInfo, balanceInfo)){
            return new AccountInfoDto(nameInfo, leverageInfo, balanceInfo, false);
        } else return new AccountInfoDto(nameInfo, leverageInfo, balanceInfo, true);
    }

    private boolean isStringBiggerThanZero(String nameInfo, String leverageInfo, String balanceInfo){
        return nameInfo.length()>0 || leverageInfo.length()>0 || balanceInfo.length()>0;
    }

    private String analyzeName(AccountDto accountDto){
        if(accountDto.getAccountName().length()>0){
            return "";
        } else {
            return accountNameToShort;
        }
    }

    private String analyzeLeverage(AccountDto accountDto){
        if(accountDto.getLeverage()>leverage){
            return maxLeverage;
        } else return "";
    }

    private String analyzeBalance(AccountDto accountDto){
        if(accountDto.getBalance()<0){
            return balanceOnMinus;
        } else return "";
    }
}
