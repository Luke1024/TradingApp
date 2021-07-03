package com.backend.app.controler;

import com.backend.app.domain.dto.AccountDto;
import com.backend.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/trading")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping(value="/account/{token}")
    public List<AccountDto> saveAccount(@PathVariable String token, @RequestBody AccountDto accountDto){
        return accountService.saveAccount(token, accountDto);
    }

    @PutMapping(value = "/account/{token}")
    public List<AccountDto> updateAccount(@PathVariable String token, @RequestBody AccountDto accountDto){
        return accountService.updateAccount(token, accountDto);
    }

    @DeleteMapping(value = "/account/{token}/{id}")
    public List<AccountDto> deleteAccount(@PathVariable String token, @PathVariable long id){
        return accountService.deleteAccount(token, id);
    }
}
