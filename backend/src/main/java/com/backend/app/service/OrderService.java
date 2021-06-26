package com.backend.app.service;

import com.backend.app.domain.dto.AccountDto;
import com.backend.app.domain.dto.OrderDto;
import com.backend.app.domain.dto.TradingStateDto;
import com.backend.app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    public OrderRepository orderRepository;

    public TradingStateDto saveOrder(String token, OrderDto orderDto){
        return new TradingStateDto();
    }

    public TradingStateDto updateOrder(String token, OrderDto orderDto){
        return new TradingStateDto();
    }

    public TradingStateDto deleteOrder(String token, long id){
        return new TradingStateDto();
    }

}
