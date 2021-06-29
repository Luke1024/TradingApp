package com.backend.app.service;

import com.backend.app.domain.dto.AccountDto;
import com.backend.app.domain.dto.TradingStateDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.User;
import com.backend.app.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TradingStateService {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountMapper accountMapper;

    public TradingStateDto getTradingState(String token){
        Optional<User> userOptional = userService.getUser(token);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            List<AccountDto> accountDtos = assembleAccountDtos(user);
            return new TradingStateDto(token, accountDtos);
        } else return new TradingStateDto(token, new ArrayList<>());
    }

    private List<AccountDto> assembleAccountDtos(User user){
        List<Account> accounts = user.getAccounts();
        List<AccountDto> accountDtos = new ArrayList<>();
        for(Account account : accounts){
            accountDtos.add(accountMapper.mapToAccountDtoWithOrders(account));
        }
        return accountDtos;
    }
}
