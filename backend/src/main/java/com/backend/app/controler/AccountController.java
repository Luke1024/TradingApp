package com.backend.app.controler;

import com.backend.app.domain.dto.AccountDto;
import com.backend.app.domain.dto.TradingStateDto;
import com.backend.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/trading")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping(value="/account/{token}")
    public TradingStateDto saveAccount(@PathVariable String token, @RequestBody AccountDto accountDto){
        return accountService.saveAccount(token, accountDto);
    }

    @PutMapping(value = "/account/{token}")
    public TradingStateDto updateAccount(@PathVariable String token, @RequestBody AccountDto accountDto){
        return accountService.updateAccount(token, accountDto);
    }

    @DeleteMapping(value = "/account/{token}/{id}")
    public TradingStateDto deleteAccount(@PathVariable String token, @PathVariable long id){
        return accountService.deleteAccount(token, id);
    }
}
