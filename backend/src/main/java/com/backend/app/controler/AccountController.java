package com.backend.app.controler;

import com.backend.app.domain.dto.AccountDto;
import com.backend.app.domain.dto.AccountInfoDto;
import com.backend.app.domain.dto.AccountResponseDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.mapper.AccountMapper;
import com.backend.app.service.account.AccountCorrectnessGuardService;
import com.backend.app.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://ec2-3-73-59-212.eu-central-1.compute.amazonaws.com/")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/trading")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountCorrectnessGuardService correctnessGuardService;

    @PutMapping(value="/account/info")
    public AccountInfoDto getAccountInfo(@RequestBody AccountDto accountDto){
        return getInfo(accountDto);
    }

    @GetMapping(value="/account/all/{token}")
    public List<AccountDto> getAllAccounts(@PathVariable String token){
        return accountMapper.mapToExistingAccountDtoList(accountService.getOpenedAccountsByToken(token));
    }

    @GetMapping(value="/account/{token}/{id}")
    public AccountResponseDto getAccount(@PathVariable String token, @PathVariable long id){
        Optional<Account> accountOptional = accountService.getAccount(token, id);
        if(accountOptional.isPresent()){
            return new AccountResponseDto(true, accountMapper.mapToExistingAccountDto(accountOptional.get()));
        } else {
            return new AccountResponseDto(false, new AccountDto());
        }
    }

    @PutMapping(value="/account/save/{token}")
    public AccountResponseDto accountSave(@PathVariable String token, @RequestBody AccountDto accountDto){
        AccountInfoDto accountInfoDto = getInfo(accountDto);
        if(accountInfoDto.isStatus()){
            Optional<Account> accountOptional = accountService.saveAccount(token, accountDto);
            if(accountOptional.isPresent()){
                return new AccountResponseDto(true, accountMapper.mapToExistingAccountDto(accountOptional.get()));
            }
        }
        return new AccountResponseDto(false, new AccountDto());
    }

    @PutMapping(value="/account/update/{token}")
    public AccountResponseDto accountUpdate(@PathVariable String token, @RequestBody AccountDto accountDto){
        AccountInfoDto accountInfoDto = getInfo(accountDto);
        if(accountInfoDto.isStatus()){
            Optional<Account> accountOptional = accountService.updateAccount(token, accountDto);
            if(accountOptional.isPresent()){
                return new AccountResponseDto(true, accountMapper.mapToExistingAccountDto(accountOptional.get()));
            }
        }
        return new AccountResponseDto(false, new AccountDto());
    }

    @DeleteMapping(value="/account/{token}/{id}")
    public boolean accoundDelete(@PathVariable String token, @PathVariable long id){
        return accountService.deleteAccount(token, id);
    }

    private AccountInfoDto getInfo(AccountDto accountDto){
        return correctnessGuardService.getInfo(accountDto);
    }
}
