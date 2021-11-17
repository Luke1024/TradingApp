package com.backend.app.service.admin;

import com.backend.app.domain.dto.admin.ExtendedAccountDto;
import com.backend.app.domain.dto.admin.ExtendedOrderDto;
import com.backend.app.domain.dto.admin.UserDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.AppUser;
import com.backend.app.repository.AccountRepository;
import com.backend.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<UserDto> getAllUsers(){
        Iterable<AppUser> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for(AppUser appUser : users){
            userDtos.add(new UserDto(appUser.getId(), appUser.getToken(), appUser.getUserCreationTime()));
        }
        return userDtos;
    }

    public List<ExtendedAccountDto> getAccountReportByUserId(long id){
        Optional<AppUser> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            return userOptional.get().getAccounts().stream().map(account -> new ExtendedAccountDto(
                    account.getId(),
                    account.getAccountName(),
                    account.getLeverage(),
                    account.getBalance(),
                    account.getAccountStatus(),
                    account.getCreationDate(),
                    account.getCurrencyOrders().size()
            )).collect(Collectors.toList());
        } else return new ArrayList<>();
    }

    public List<ExtendedOrderDto> getOrderReportByAccountId(long id){
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isPresent()){
            return accountOptional.get().getCurrencyOrders().stream().map(order -> new ExtendedOrderDto(
                    order.getId(),
                    order.getCurrency(),
                    order.getLot(),
                    order.getTpPips(),
                    order.getTpVal(),
                    order.getSlPips(),
                    order.getSlVal(),
                    order.getProfit(),
                    order.getShortLong(),
                    order.getOrderState(),
                    order.getCreationDate())
            ).collect(Collectors.toList());
        } else return new ArrayList<>();
    }
}
