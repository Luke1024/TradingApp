package com.backend.app.service.order;

import com.backend.app.domain.dto.OrderDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.Currency_Order;
import com.backend.app.mapper.OrderMapper;
import com.backend.app.repository.OrderRepository;
import com.backend.app.service.TradingStateService;
import com.backend.app.service.account.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    private TradingStateService tradingStateService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderMapper orderMapper;

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    public Optional<Currency_Order> getOrder(String token, long id){
        return orderRepository.findById(id);
    }

    //use account id for creation
    public Optional<Currency_Order> saveOrder(String token, OrderDto orderDto){
        Optional<Account> accountOptional = accountService.getAccount(token,orderDto.getAccountId());
        if(accountOptional.isPresent()){
            Account account = accountOptional.get();
            Currency_Order order = orderRepository.save(orderMapper.mapToNewOrder(orderDto, account));
            if(order != null) {
                return Optional.of(order);
            }
        } else {
            logger.warn("Account not found.");
        }
        return Optional.empty();
    }

    public Optional<Currency_Order> updateOrder(String token, OrderDto orderDto){
        Optional<Currency_Order> currency_order = orderMapper.mapToExistingOrder(orderDto);
        if(currency_order.isPresent()){
            Currency_Order order = orderRepository.save(currency_order.get());
            if(order != null){
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