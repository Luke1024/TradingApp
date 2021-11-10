package com.backend.app.service.admin;

import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.CurrencyOrder;
import com.backend.app.domain.entity.User;
import com.backend.app.repository.AccountRepository;
import com.backend.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<User> getAllUsers(){
        return (List<User>) userRepository.findAll();
    }

    public List<Account> getAccountReportByUserId(long id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            return userOptional.get().getAccounts();
        } else return new ArrayList<>();
    }

    public List<CurrencyOrder> getOrderReportByAccountId(long id){
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isPresent()){
            return accountOptional.get().getCurrencyOrders();
        } else return new ArrayList<>();
    }
}
