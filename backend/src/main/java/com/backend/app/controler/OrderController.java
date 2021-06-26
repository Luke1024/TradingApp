package com.backend.app.controler;

import com.backend.app.domain.dto.OrderDto;
import com.backend.app.domain.dto.TradingStateDto;
import com.backend.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/trading")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/order/{token}")
    public TradingStateDto saveAccount(@PathVariable String token, @RequestBody OrderDto orderDto) {
        return orderService.saveOrder(token, orderDto);
    }

    @PutMapping(value = "/order/{token}")
    public TradingStateDto updateAccount(@PathVariable String token, @RequestBody OrderDto orderDto) {
        return orderService.updateOrder(token, orderDto);
    }

    @DeleteMapping(value = "/order/{token}/{id}")
    public TradingStateDto deleteAccount(@PathVariable String token, @PathVariable long id) {
        return orderService.deleteOrder(token, id);
    }
}
