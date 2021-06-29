package com.backend.app.mapper;

import com.backend.app.domain.dto.AccountDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AccountMapper {

    @Autowired
    private OrderMapper orderMapper;

    public Account mapToNewAccount(AccountDto accountDto, User user){
        return new Account(
                accountDto.getAccountName(),
                accountDto.getLeverage(),
                accountDto.getBalance(),
                accountDto.getState(),
                accountDto.getMessage(),
                new ArrayList<>(),
                user
        );
    }

    public AccountDto mapToAccountDtoWithOrders(Account account){
        return new AccountDto(
                account.getId(),
                account.getAccountName(),
                account.getLeverage(),
                account.getBalance(),
                account.getState(),
                account.getMessage(),
                orderMapper.mapToOrderDtoList(account.getCurrencyOrders())
        );
    }
}
