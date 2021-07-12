package com.backend.app.service;

import com.backend.app.domain.dto.OrderDto;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.Currency_Order;
import com.backend.app.repository.AccountRepository;
import com.backend.app.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderLimitsComputingService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Logger logger = LoggerFactory.getLogger(OrderLimitsComputingService.class);

    public Optional<AvailableParameters> computeOrderAvailableParameters(OrderDto orderDto){
        Optional<Account> accountInWhichTheOrderIsOpened = getAccount(orderDto);
        Optional<Currency_Order> actualOrder = getActualOrder(orderDto);
        if(accountInWhichTheOrderIsOpened.isPresent() && actualOrder.isPresent()){
            return Optional.of(computeParameters(orderDto, accountInWhichTheOrderIsOpened.get(), actualOrder.get()));
        } else return Optional.empty();
    }

    private AvailableParameters computeParameters(OrderDto orderDto, Account account, Currency_Order order){
        double lotMin = computeLotMin(orderDto ,account, order);
        double lotMax = computeLotMax(orderDto ,account, order);
        int tpPipsMax = computeTpPipsMax(orderDto ,account, order);
        int tpPipsMin = computeTpPipsMin(orderDto ,account, order);
        int slPipsMax = computeSlPipsMax(orderDto ,account, order);
        int slPipsMin = computeSlPipsMin(orderDto ,account, order);
        double valMultiplier = computeValMultiplier(orderDto ,account, order);
        return new AvailableParameters(lotMin, lotMax, tpPipsMax, tpPipsMin, slPipsMax, slPipsMin, valMultiplier);
    }

    private double computeLotMin(OrderDto orderDto, Account account, Currency_Order order){
        return 0;
    }

    private double computeLotMax(OrderDto orderDto,Account account, Currency_Order order){
        return 0;
    }

    private int computeTpPipsMax(OrderDto orderDto,Account account, Currency_Order order){
        return 0;
    }

    private int computeTpPipsMin(OrderDto orderDto,Account account, Currency_Order order){
        return 0;
    }

    private int computeSlPipsMax(OrderDto orderDto,Account account, Currency_Order order){
        return 0;
    }

    private int computeSlPipsMin(OrderDto orderDto,Account account, Currency_Order order){
        return 0;
    }

    private double computeValMultiplier(OrderDto orderDto,Account account, Currency_Order order){
        //implement function
        return 1;
    }

    private Optional<Account> getAccount(OrderDto orderDto){
        return accountRepository.findById(orderDto.getAccountId());
    }

    private Optional<Currency_Order> getActualOrder(OrderDto orderDto){
        return orderRepository.findById(orderDto.getId());
    }

    class AvailableParameters {
        double lotMin;
        double lotMax;
        int tpPipsMax;
        int tpPipsMin;
        int slPipsMax;
        int slPipsMin;
        double valMultiplier;

        public AvailableParameters(double lotMin, double lotMax, int tpPipsMax, int tpPipsMin,
                                   int slPipsMax, int slPipsMin, double valMultiplier) {
            this.lotMin = lotMin;
            this.lotMax = lotMax;
            this.tpPipsMax = tpPipsMax;
            this.tpPipsMin = tpPipsMin;
            this.slPipsMax = slPipsMax;
            this.slPipsMin = slPipsMin;
            this.valMultiplier = valMultiplier;
        }
    }
}
