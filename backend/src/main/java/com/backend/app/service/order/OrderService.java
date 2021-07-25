package com.backend.app.service.order;

import com.backend.app.domain.dto.OrderDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.CurrencyOrder;
import com.backend.app.domain.enums.OrderState;
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

    public Optional<CurrencyOrder> getOrder(String token, long id){
        return orderRepository.findByIdArchivedFalse(id);
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
