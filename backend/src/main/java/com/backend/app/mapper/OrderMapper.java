package com.backend.app.mapper;

import com.backend.app.domain.dto.OrderDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.Currency_Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    public Currency_Order mapToNewOrder(OrderDto orderDto, Account account){
        return new Currency_Order(
                orderDto.getCurrency(),
                orderDto.getLot(),
                orderDto.getTpPips(),
                orderDto.getTpVal(),
                orderDto.getSlPips(),
                orderDto.getSlVal(),
                orderDto.getProfit(),
                orderDto.getState(),
                account);
    }

    public List<OrderDto> mapToOrderDtoList(List<Currency_Order> orders){
        List<OrderDto> orderDtos = new ArrayList<>();
        for(Currency_Order order: orders){
            orderDtos.add(mapToDto(order));
        }
        return orderDtos;
    }

    public OrderDto mapToDto(Currency_Order order){
        return new OrderDto(
                order.getId(),
                order.getCurrency(),
                order.getLot(),
                order.getTpPips(),
                order.getTpVal(),
                order.getSlPips(),
                order.getSlVal(),
                order.getProfit(),
                order.getState(),
                null
        );
    }
}
