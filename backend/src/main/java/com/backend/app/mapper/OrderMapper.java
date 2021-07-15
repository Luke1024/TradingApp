package com.backend.app.mapper;

import com.backend.app.domain.ShortLong;
import com.backend.app.domain.dto.OrderDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.Currency_Order;
import com.backend.app.repository.AccountRepository;
import com.backend.app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderMapper {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Currency_Order mapToNewOrder(OrderDto orderDto, Account account){
        return new Currency_Order(
                orderDto.getCurrency(),
                orderDto.getLot(),
                orderDto.getTpPips(),
                orderDto.getTpVal(),
                orderDto.getSlPips(),
                orderDto.getSlVal(),
                orderDto.getProfit(),
                booleanOrderToEnumMapper(orderDto.isLongOrder()),
                account);
    }

    private ShortLong booleanOrderToEnumMapper(boolean longOrder){
        if(longOrder){
            return ShortLong.LONG;
        } else {
            return ShortLong.SHORT;
        }
    }

    public Optional<Currency_Order> mapToExistingOrder(OrderDto orderDto){
        Optional<Currency_Order> orderOptional = orderRepository.findById(orderDto.getId());
        if(orderOptional.isPresent()){
            return mapOrder(orderOptional.get(), orderDto);
        } else {
            return Optional.empty();
        }
    }

    //analyze what could and what could not be set
    private Optional<Currency_Order> mapOrder(Currency_Order existingOrder, OrderDto orderDto){
        existingOrder.setTpPips(orderDto.getTpPips());
        existingOrder.setSlPips(orderDto.getSlPips());
        return Optional.of(existingOrder);
    }

    public List<OrderDto> mapToOrderDtoList(List<Currency_Order> orders){
        List<OrderDto> orderDtos = new ArrayList<>();
        for(Currency_Order order: orders){
            Optional<OrderDto> orderDtoOptional = mapToDtoFromExistingOrder(order);
            if(orderDtoOptional.isPresent()) {
                orderDtos.add(orderDtoOptional.get());
            }
        }
        return orderDtos;
    }

    public Optional<OrderDto> mapToDtoFromExistingOrder(Currency_Order order){
        Account account = order.getAccount();
        if(account != null){
            return Optional.of(new OrderDto(
                    account.getId(),
                    order.getId(),
                    order.getCurrency(),
                    order.getLot(),
                    order.getTpPips(),
                    order.getTpVal(),
                    order.getSlPips(),
                    order.getSlVal(),
                    order.getProfit(),
                    enumToBooleanOrderMapper(order.getShortLong()),
                    true));
        } else {
            return Optional.empty();
        }
    }

    private boolean enumToBooleanOrderMapper(ShortLong shortLong){
        if(shortLong==ShortLong.LONG){
            return true;
        } else return false;
    }
}
