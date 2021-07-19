package com.backend.app.mapper;

import com.backend.app.domain.dto.OrderInfoDto;
import com.backend.app.domain.enums.OrderState;
import com.backend.app.domain.enums.ShortLong;
import com.backend.app.domain.dto.OrderDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.CurrencyOrder;
import com.backend.app.repository.AccountRepository;
import com.backend.app.repository.OrderRepository;
import com.backend.app.service.order.orderCorrectnessGuardService.OrderCorrectnessGuardService;
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

    @Autowired
    private OrderCorrectnessGuardService orderCorrectnessGuardService;

    public Optional<CurrencyOrder> mapToNewOrder(OrderDto orderDto, Account account) {
        OrderInfoDto orderInfoDto = orderCorrectnessGuardService.getInfo(orderDto);
        if (orderInfoDto.isStatus()) {
            return Optional.of(new CurrencyOrder(
                    orderDto.getCurrency(),
                    orderDto.getLot(),
                    orderDto.getTpPips(),
                    orderInfoDto.getTpVal(),
                    orderDto.getSlPips(),
                    orderInfoDto.getSlVal(),
                    booleanOrderToEnumMapper(orderDto.isLongOrder()),
                    OrderState.OPENED,
                    account));
        }
        return Optional.empty();
    }

    private ShortLong booleanOrderToEnumMapper(boolean longOrder){
        if(longOrder){
            return ShortLong.LONG;
        } else {
            return ShortLong.SHORT;
        }
    }

    public Optional<CurrencyOrder> mapToExistingOrder(OrderDto orderDto){
        Optional<CurrencyOrder> orderOptional = orderRepository.findById(orderDto.getId());
        if(orderOptional.isPresent()){
            return mapExistingOrder(orderOptional.get(), orderDto);
        } else {
            return Optional.empty();
        }
    }

    //analyze what could and what could not be set
    private Optional<CurrencyOrder> mapExistingOrder(CurrencyOrder existingOrder, OrderDto orderDto){
        OrderInfoDto orderInfoDto = orderCorrectnessGuardService.getInfo(orderDto);
        if(orderInfoDto.isStatus()) {
            existingOrder.setTpPips(orderDto.getTpPips());
            existingOrder.setTpVal(orderInfoDto.getTpVal());
            existingOrder.setSlPips(orderDto.getSlPips());
            existingOrder.setSlVal(orderDto.getSlVal());
            return Optional.of(existingOrder);
        } else {
            return Optional.empty();
        }
    }

    public List<OrderDto> mapToOrderDtoList(List<CurrencyOrder> orders){
        List<OrderDto> orderDtos = new ArrayList<>();
        for(CurrencyOrder order: orders){
            Optional<OrderDto> orderDtoOptional = mapToDtoFromExistingOrder(order);
            if(orderDtoOptional.isPresent()) {
                orderDtos.add(orderDtoOptional.get());
            }
        }
        return orderDtos;
    }

    public Optional<OrderDto> mapToDtoFromExistingOrder(CurrencyOrder order){
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
