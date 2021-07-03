package com.backend.app.controler;

import com.backend.app.domain.dto.AccountDto;
import com.backend.app.domain.dto.OrderDto;
import com.backend.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/trading")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/order/{token}/{account_id}")
    public List<AccountDto> saveAccount(@PathVariable String token, @PathVariable long account_id, @RequestBody OrderDto orderDto) {
        return orderService.saveOrder(token, account_id, orderDto);
    }

    @PutMapping(value = "/order/{token}")
    public List<AccountDto> updateAccount(@PathVariable String token, @RequestBody OrderDto orderDto) {
        return orderService.updateOrder(token, orderDto);
    }

    @DeleteMapping(value = "/order/{token}/{id}")
    public List<AccountDto> deleteAccount(@PathVariable String token, @PathVariable long id) {
        return orderService.deleteOrder(token, id);
    }
}
