package com.backend.app.service;

import com.backend.app.domain.State;
import com.backend.app.domain.dto.AccountDto;
import com.backend.app.domain.dto.OrderDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.Currency_Order;
import com.backend.app.mapper.OrderMapper;
import com.backend.app.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private OrderModdingService orderModdingService;

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    //use account id for creation
    public List<AccountDto> saveOrder(String token, long account_id, OrderDto orderDto){
        if(orderDtoNullCheck(orderDto)){
            if(checkStateForSaving(orderDto)){
                Optional<Account> accountOptional = accountService.getAccount(account_id);
                if(accountOptional.isPresent()){
                    Account account = accountOptional.get();
                    orderRepository.save(orderMapper.mapToNewOrder(orderDto, account));
                } else {
                    logger.warn("Account not found.");
                }
            }
        }
        return tradingStateService.getTradingState(token);
    }

    private boolean orderDtoNullCheck(OrderDto orderDto){
        if(orderDto != null){
            return true;
        } else {
            logger.warn("OrderDto is null.");
            return false;
        }
    }

    private boolean checkStateForSaving(OrderDto orderDto){
        if(orderDto.getState()== State.CREATION){
            return true;
        } else {
            logger.warn("Order must be in creation mode for saving.");
            return false;
        }
    }

    public List<AccountDto> updateOrder(String token, OrderDto orderDto){
        if(orderDtoNullCheck(orderDto)){
            Optional<Currency_Order> orderOptional = getOrder(orderDto);
            if(orderOptional.isPresent()){
                mapAndUpdateOrder(orderOptional.get(), orderDto);
            } else {
                logger.warn("Order not found.");
            }
        }
        return tradingStateService.getTradingState(token);
    }

    private void mapAndUpdateOrder(Currency_Order currency_order, OrderDto orderDto){
        Currency_Order orderMofified = orderModdingService.mod(currency_order, orderDto);;
        orderRepository.save(orderMofified);
    }

    private Optional<Currency_Order> getOrder(OrderDto orderDto){
        return orderRepository.findById(orderDto.getId());
    }

    public List<AccountDto> deleteOrder(String token, long id){
        Optional<Currency_Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isPresent()){
            orderRepository.deleteById(id);
        } else {
            logger.warn("Order to delete not found.");
        }
        return tradingStateService.getTradingState(token);
    }
}
