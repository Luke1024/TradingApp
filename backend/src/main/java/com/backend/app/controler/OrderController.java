package com.backend.app.controler;

import com.backend.app.domain.dto.OrderDto;
import com.backend.app.domain.dto.OrderInfoDto;
import com.backend.app.domain.dto.OrderResponseDto;
import com.backend.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/trading")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PutMapping(value="/order/info/{token}")
    public OrderInfoDto getOrderInfo(@PathVariable String token, @RequestBody OrderDto orderDto){
        return orderService.getOrderInfo(token, orderDto);
    }

    @GetMapping(value="/order/{token}/{id}")
    public OrderResponseDto getOrder(@PathVariable String token, @PathVariable long id){
        return orderService.getOrder(token, id);
    }

    @PutMapping(value="/order/save/{token}")
    public OrderResponseDto saveOrder(@PathVariable String token, @RequestBody OrderDto orderDto){
        return orderService.saveOrder(token, orderDto);
    }

    @PutMapping(value="/order/update/{token}")
    public OrderResponseDto orderUpdate(@PathVariable String token, @RequestBody OrderDto orderDto){
        return orderService.updateOrder(token, orderDto);
    }

    @DeleteMapping(value="/order/{token}/{id}")
    public boolean deleteOrder(@PathVariable String token, @PathVariable long id){
        return orderService.deleteOrder(token, id);
    }
}
