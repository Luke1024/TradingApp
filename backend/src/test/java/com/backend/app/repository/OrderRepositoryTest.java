package com.backend.app.repository;

import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.CurrencyOrder;
import com.backend.app.domain.enums.OrderState;
import com.backend.app.domain.enums.ShortLong;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void findByIdArchivedFalse(){

        CurrencyOrder order = new CurrencyOrder("",0.1,1,0.1,1,0.1, ShortLong.LONG, OrderState.OPENED, new Account());

        orderRepository.save(order);

        Assert.assertTrue(orderRepository.findByIdArchivedFalse(order.getId()).isPresent());
    }

    @Test
    public void findByIdArchivedTrue(){

        CurrencyOrder order = new CurrencyOrder("",0.1,1,0.1,1,0.1, ShortLong.LONG, OrderState.ARCHIVED, new Account());

        orderRepository.save(order);

        Assert.assertFalse(orderRepository.findByIdArchivedFalse(order.getId()).isPresent());
    }
}