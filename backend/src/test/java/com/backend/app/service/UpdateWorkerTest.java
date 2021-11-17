package com.backend.app.service;

import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.AppUser;
import com.backend.app.domain.entity.CurrencyOrder;
import com.backend.app.domain.enums.AccountStatus;
import com.backend.app.domain.enums.OrderState;
import com.backend.app.domain.enums.ShortLong;
import com.backend.app.repository.AccountRepository;
import com.backend.app.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateWorkerTest {

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private UpdateWorker updateWorker;

    @Test
    public void simulateWorkerUpdate(){

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        ArgumentCaptor<CurrencyOrder> orderCaptor = ArgumentCaptor.forClass(CurrencyOrder.class);

        AppUser appUser = new AppUser("", new ArrayList<>());
        Account account = new Account("account",10, 1000, appUser, AccountStatus.OPENED);

        //change 10 pips on 0.2lot buy eurusd should generate 20$ profit
        //account balance should change to 1020$
        CurrencyOrder order1 = new CurrencyOrder("EUR/USD",
                0.2, 500, 1000, -200, -400, ShortLong.LONG, OrderState.OPENED, account);

        //should generate 10$ profit
        CurrencyOrder order2 = new CurrencyOrder("EUR/USD",
                0.1, 500, 500, -200, -200, ShortLong.LONG, OrderState.OPENED, account);

        //should generate 10$ loss
        CurrencyOrder order3 = new CurrencyOrder("EUR/USD",
                0.1, -500, -500, 200, 200, ShortLong.SHORT, OrderState.OPENED, account);


        when(accountRepository.findAll()).thenReturn(Arrays.asList(account));

        updateWorker.enable();
        updateWorker.updateChangeReceived(0.001);

        verify(accountRepository).save(accountCaptor.capture());
        verify(orderRepository, atLeast(3)).save(orderCaptor.capture());

        Assert.assertEquals(1020, accountCaptor.getValue().getBalance(), 0.01);
        Assert.assertEquals(20, orderCaptor.getAllValues().get(0).getProfit(), 0.01);
        Assert.assertEquals(10, orderCaptor.getAllValues().get(1).getProfit(), 0.01);
        Assert.assertEquals(-10, orderCaptor.getAllValues().get(2).getProfit(), 0.01);
    }

    @Test
    public void simulateTpAndSLHitInUpDirection(){

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        ArgumentCaptor<CurrencyOrder> orderCaptor = ArgumentCaptor.forClass(CurrencyOrder.class);

        AppUser appUser = new AppUser("", new ArrayList<>());
        Account account = new Account("account",10, 1000, appUser, AccountStatus.OPENED);

        CurrencyOrder order1 = new CurrencyOrder("EUR/USD",
                0.2, 50, 100, -200, -400, ShortLong.LONG, OrderState.OPENED, account);

        CurrencyOrder order2 = new CurrencyOrder("EUR/USD",
                0.1, -50, -50, 50, 50, ShortLong.SHORT, OrderState.OPENED, account);

        CurrencyOrder order3 = new CurrencyOrder("EUR/USD",
                0.1, 500, 500, -200, -200, ShortLong.LONG, OrderState.OPENED, account);

        when(accountRepository.findAll()).thenReturn(Arrays.asList(account));

        updateWorker.enable();
        updateWorker.updateChangeReceived(0.005);

        verify(accountRepository).save(accountCaptor.capture());
        verify(orderRepository, atLeast(3)).save(orderCaptor.capture());

        Assert.assertEquals(100, orderCaptor.getAllValues().get(0).getProfit(), 0.01);
        Assert.assertEquals(OrderState.CLOSED, orderCaptor.getAllValues().get(0).getOrderState());

        Assert.assertEquals(-50, orderCaptor.getAllValues().get(1).getProfit(), 0.01);
        Assert.assertEquals(OrderState.CLOSED, orderCaptor.getAllValues().get(1).getOrderState());

        Assert.assertEquals(50, orderCaptor.getAllValues().get(2).getProfit(), 0.01);
        Assert.assertEquals(OrderState.OPENED, orderCaptor.getAllValues().get(2).getOrderState());
    }

    @Test
    public void simulateTpAndSlHitInDownDirection(){

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        ArgumentCaptor<CurrencyOrder> orderCaptor = ArgumentCaptor.forClass(CurrencyOrder.class);

        AppUser appUser = new AppUser("", new ArrayList<>());
        Account account = new Account("account",10, 1000, appUser, AccountStatus.OPENED);

        CurrencyOrder order1 = new CurrencyOrder("EUR/USD",
                0.2, 50, 100, -50, -100, ShortLong.LONG, OrderState.OPENED, account);

        CurrencyOrder order2 = new CurrencyOrder("EUR/USD",
                0.1, -50, -50, 50, 50, ShortLong.SHORT, OrderState.OPENED, account);

        CurrencyOrder order3 = new CurrencyOrder("EUR/USD",
                0.1, 500, 500, -200, -200, ShortLong.LONG, OrderState.OPENED, account);

        when(accountRepository.findAll()).thenReturn(Arrays.asList(account));

        updateWorker.enable();
        updateWorker.updateChangeReceived(-0.005);

        verify(accountRepository).save(accountCaptor.capture());
        verify(orderRepository, atLeast(3)).save(orderCaptor.capture());

        Assert.assertEquals(-100, orderCaptor.getAllValues().get(0).getProfit(), 0.01);
        Assert.assertEquals(OrderState.CLOSED, orderCaptor.getAllValues().get(0).getOrderState());

        Assert.assertEquals(50, orderCaptor.getAllValues().get(1).getProfit(), 0.01);
        Assert.assertEquals(OrderState.CLOSED, orderCaptor.getAllValues().get(1).getOrderState());

        Assert.assertEquals(-50, orderCaptor.getAllValues().get(2).getProfit(), 0.01);
        Assert.assertEquals(OrderState.OPENED, orderCaptor.getAllValues().get(2).getOrderState());
    }
}