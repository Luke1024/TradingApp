package com.backend.app.service;

import com.backend.app.domain.State;
import com.backend.app.domain.dto.OrderDto;
import com.backend.app.domain.dto.TradingStateDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.Currency_Order;
import com.backend.app.mapper.OrderMapper;
import com.backend.app.repository.OrderRepository;
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

    //use account id for creation
    public TradingStateDto saveOrder(String token, long account_id, OrderDto orderDto){
        if(orderDtoNullCheck(orderDto)){
            if(checkStateForSaving(orderDto)){
                Optional<Account> accountOptional = accountService.getAccount(account_id);
                if(accountOptional.isPresent()){
                    Account account = accountOptional.get();
                    account.addOrder(orderMapper.mapToNewOrder(orderDto, account));
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

    public TradingStateDto updateOrder(String token, OrderDto orderDto){
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
        currency_order.setTpPips(orderDto.getTpPips());
        currency_order.setTpVal(orderDto.getTpVal());
        currency_order.setProfit(orderDto.getProfit());
        currency_order.setState(orderDto.getState());
        currency_order.setMessage(orderDto.getMessage());
        orderRepository.save(currency_order);
    }

    private Optional<Currency_Order> getOrder(OrderDto orderDto){
        return orderRepository.findById(orderDto.getId());
    }

    public TradingStateDto deleteOrder(String token, long id){
        Optional<Currency_Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isPresent()){
            orderRepository.deleteById(id);
        } else {
            logger.warn("Order to delete not found.");
        }
        return tradingStateService.getTradingState(token);
    }
}
