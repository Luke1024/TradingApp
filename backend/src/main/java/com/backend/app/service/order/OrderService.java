package com.backend.app.service.order;

import com.backend.app.domain.dto.OrderDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.CurrencyOrder;
import com.backend.app.domain.entity.User;
import com.backend.app.domain.enums.OrderState;
import com.backend.app.mapper.OrderMapper;
import com.backend.app.repository.OrderRepository;
import com.backend.app.service.UserService;
import com.backend.app.service.account.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderMapper orderMapper;

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    public Optional<CurrencyOrder> getOrder(String token, long id){
        return orderRepository.findByIdArchivedFalse(id);
    }

    public List<CurrencyOrder> getAllOpenOrdersByTokenAndAccountId(String token, long account_id){
        Optional<User> userOptional = userService.getUser(token);
        if(userOptional.isPresent()){
            List<Account> accounts = userOptional.get().getAccounts();
            Optional<Account> accountToFound = Optional.empty();
            for(Account account : accounts){
                if(account.getId()==account_id){
                    accountToFound = Optional.of(account);
                    break;
                }
            }
            if(accountToFound.isPresent()){
                return accountToFound.get().getCurrencyOrders().stream()
                        .filter(order -> order.getOrderState()==OrderState.OPENED).collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }

    public List<CurrencyOrder> getAllClosedOrdersByTokenAndAccountId(String token, long account_id){
        Optional<User> userOptional = userService.getUser(token);
        if(userOptional.isPresent()){
            List<Account> accounts = userOptional.get().getAccounts();
            Optional<Account> accountToFound = Optional.empty();
            for(Account account : accounts){
                if(account.getId()==account_id){
                    accountToFound = Optional.of(account);
                    break;
                }
            }
            if(accountToFound.isPresent()){
                return accountToFound.get().getCurrencyOrders().stream()
                        .filter(order -> order.getOrderState()==OrderState.CLOSED).collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }

    //use account id for creation
    public Optional<CurrencyOrder> saveOrder(String token, OrderDto orderDto){
        Optional<Account> accountOptional = accountService.getAccount(token,orderDto.getAccountId());
        if(accountOptional.isPresent()){
            Account account = accountOptional.get();
            Optional<CurrencyOrder> orderOptional = orderMapper.mapToNewOrder(orderDto, account);
            if(orderOptional.isPresent()) {
                return Optional.of(orderRepository.save(orderOptional.get()));
            }
        } else {
            logger.warn("Account not found.");
        }
        return Optional.empty();
    }

    public Optional<CurrencyOrder> updateOrder(String token, OrderDto orderDto){
        Optional<CurrencyOrder> currency_order = orderMapper.mapToExistingOrder(orderDto);
        if(currency_order.isPresent()){
            if(currency_order.get().getOrderState()== OrderState.OPENED) {
                CurrencyOrder order = orderRepository.save(currency_order.get());
                return Optional.of(order);
            }
        }
        return Optional.empty();
    }

    public boolean deleteOrder(String token, long id){
        orderRepository.deleteById(id);
        return true;
    }
}
