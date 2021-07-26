package com.backend.app.controler;

import com.backend.app.domain.dto.OrderDto;
import com.backend.app.domain.dto.OrderInfoDto;
import com.backend.app.domain.dto.OrderResponseDto;
import com.backend.app.domain.entity.CurrencyOrder;
import com.backend.app.mapper.OrderMapper;
import com.backend.app.service.order.orderCorrectnessGuardService.OrderCorrectnessGuardService;
import com.backend.app.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/trading")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderCorrectnessGuardService orderCorrectnessGuardService;

    @PutMapping(value="/order/info")
    public OrderInfoDto getOrderInfo(@RequestBody OrderDto orderDto){
        return getInfo(orderDto);
    }

    @GetMapping(value="/order/all/{token}/{account_id}")
    public List<OrderDto> getAllOrdersByToken(@PathVariable String token, @PathVariable long account_id){
        return orderMapper.mapToExistingOrderDtoList(orderService.getAllOrdersByTokenAndAccountId(token, account_id));
    }

    @GetMapping(value="/order/{token}/{id}")
    public OrderResponseDto getOrder(@PathVariable String token, @PathVariable long id){
        Optional<CurrencyOrder> orderOptional = orderService.getOrder(token, id);
        if(orderOptional.isPresent()) {
            Optional<OrderDto> orderDtoOptional = orderMapper.mapToDtoFromExistingOrder(orderOptional.get());
            if(orderDtoOptional.isPresent()){
                return new OrderResponseDto(true, orderDtoOptional.get());
            }
        }
        return new OrderResponseDto(false, new OrderDto());
    }

    @PutMapping(value="/order/save/{token}")
    public OrderResponseDto saveOrder(@PathVariable String token, @RequestBody OrderDto orderDto){
        OrderInfoDto orderInfoDto = getInfo(orderDto);
        if(orderInfoDto.isStatus()){
            Optional<CurrencyOrder> orderOptional = orderService.saveOrder(token, orderDto);
            if(orderOptional.isPresent()){
                Optional<OrderDto> orderDtoOptional = orderMapper.mapToDtoFromExistingOrder(orderOptional.get());
                if(orderDtoOptional.isPresent()){
                    return new OrderResponseDto(true, orderDtoOptional.get());
                }
            }
        }
        return new OrderResponseDto(false, new OrderDto());
    }

    @PutMapping(value="/order/update/{token}")
    public OrderResponseDto orderUpdate(@PathVariable String token, @RequestBody OrderDto orderDto){
        OrderInfoDto orderInfoDto = getInfo(orderDto);
        if(orderInfoDto.isStatus()){
            Optional<CurrencyOrder> orderOptional = orderService.updateOrder(token, orderDto);
            if(orderOptional.isPresent()){
                Optional<OrderDto> orderDtoOptional = orderMapper.mapToDtoFromExistingOrder(orderOptional.get());
                if(orderDtoOptional.isPresent()){
                    return new OrderResponseDto(true, orderDtoOptional.get());
                }
            }
        }
        return new OrderResponseDto(false, new OrderDto());
    }

    @DeleteMapping(value="/order/{token}/{id}")
    public boolean deleteOrder(@PathVariable String token, @PathVariable long id){
        return orderService.deleteOrder(token, id);
    }

    private OrderInfoDto getInfo(OrderDto orderDto){
        return orderCorrectnessGuardService.getInfo(orderDto);
    }
}
