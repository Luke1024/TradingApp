package com.backend.app.service;

import com.backend.app.domain.enums.OrderState;
import com.backend.app.domain.enums.ShortLong;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.CurrencyOrder;
import com.backend.app.repository.AccountRepository;
import com.backend.app.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateWorker {

    private boolean enabled = false;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Logger logger = LoggerFactory.getLogger(UpdateWorker.class);

    public void updateChangeReceived(double valueChange){
        if(enabled){
            if(valueChange != 0){
                updateAccountsAndOrdersBasedOnReceivedDataPoint(valueChange);
            }
        }
    }

    public void enable(){
        enabled = true;
    }

    private void updateAccountsAndOrdersBasedOnReceivedDataPoint(double valueChange){
        Iterable<Account> accounts = accountRepository.findAll();
        for (Account account : accounts) {
            updateOrdersAndAccountBalance(account, valueChange);
        }
        logger.info("Update pass executed.");
    }

    private void updateOrdersAndAccountBalance(Account account, double valueChange){
        List<CurrencyOrder> orders = account.getCurrencyOrders();
        for(CurrencyOrder order : orders){
            if(order.getOrderState()==OrderState.OPENED) {
                updateOrderAndAccountBalance(account, order, valueChange);
            }
        }
        accountRepository.save(account);
    }

    private void updateOrderAndAccountBalance(Account account, CurrencyOrder order, double valueChange){
        double orderBalanceChange = computeOrderBalanceChange(order, valueChange);
        updateOrderBalance(order, orderBalanceChange);
        updateAccountBalance(account, orderBalanceChange);
        closeOrderIfTakeProfitOrStopLossHit(order);
        orderRepository.save(order);
    }

    private double computeOrderBalanceChange(CurrencyOrder order, double valueChange){
        int pipsChange = computePipsChangeOnLong(valueChange);
        double lot = order.getLot();

        if(order.getShortLong()== ShortLong.LONG){
            return pipsChange * lot * 10;
        } else {
            return -pipsChange * lot * 10;
        }
    }

    private int computePipsChangeOnLong(double valueChange){
        return (int) (valueChange * 10000);
    }


    private void updateOrderBalance(CurrencyOrder order, double orderBalanceChange){
        double balanceToUpdate = order.getProfit();
        double newBalance = balanceToUpdate + orderBalanceChange;
        order.setProfit(newBalance);
    }

    private void updateAccountBalance(Account account, double orderBalancheChange){
        double balanceToUpdate = account.getBalance();
        double newBalance = balanceToUpdate + orderBalancheChange;
        account.setBalance(newBalance);
    }

    private void closeOrderIfTakeProfitOrStopLossHit(CurrencyOrder order){
        double balance = order.getProfit();
        double tpVal = order.getTpVal();
        double slVal = order.getSlVal();

        if(checkIfTakeProfitHit(balance, tpVal) || checkIfStopLossHit(balance, slVal)){
            orderClose(order);
        }
    }

    private boolean checkIfTakeProfitHit(double balance, double tpVal){
        if(tpVal > 0){
            if(balance >= tpVal){
                return true;
            }
        } else {
            if(balance <= tpVal){
                return true;
            }
        }
        return false;
    }

    private boolean checkIfStopLossHit(double balance, double slVal){
        if(slVal > 0){
            if(balance >= slVal){
                return true;
            }
        } else {
            if(balance <= slVal){
                return true;
            }
        }
        return false;
    }

    private void orderClose(CurrencyOrder order){
        order.setOrderState(OrderState.CLOSED);
    }
}
