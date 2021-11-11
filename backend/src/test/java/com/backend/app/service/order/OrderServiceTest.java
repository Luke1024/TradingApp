package com.backend.app.service.order;

import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.CurrencyOrder;
import com.backend.app.domain.enums.OrderState;
import com.backend.app.domain.enums.ShortLong;
import com.backend.app.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void getOpenedOrder() {
        CurrencyOrder currencyOrder = new CurrencyOrder(
                "EUR/USD",
                0.01,
                100,
                1.1,
                100,
                0.9,
                ShortLong.LONG,
                OrderState.OPENED,
                new Account());

        orderRepository.save(currencyOrder);

        Assert.assertEquals(currencyOrder.getId(),orderService.getOrder("", currencyOrder.getId()).get().getId());
    }

    @Test
    public void getArchivedOrder() {
        CurrencyOrder currencyOrder = new CurrencyOrder(
                "EUR/USD",
                0.01,
                100,
                1.1,
                100,
                0.9,
                ShortLong.LONG,
                OrderState.ARCHIVED,
                new Account());

        orderRepository.save(currencyOrder);

        Assert.assertEquals(Optional.empty(),orderService.getOrder("", currencyOrder.getId()));
    }
}