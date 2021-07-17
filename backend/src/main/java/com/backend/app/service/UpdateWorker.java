package com.backend.app.service;

import com.backend.app.domain.DataPoint;
import com.backend.app.domain.enums.OrderState;
import com.backend.app.domain.enums.ShortLong;
import com.backend.app.domain.entity.Account;
import com.backend.app.domain.entity.Currency_Order;
import com.backend.app.repository.AccountRepository;
import com.backend.app.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateWorker {

    private boolean enabled = false;

    private AccountRepository accountRepository;
    private OrderRepository orderRepository;

    public void pointReceived(DataPoint dataPoint){
        if(enabled){
            if(dataPoint != null){
                updateAccountsAndOrdersBasedOnReceivedDataPoint(dataPoint);
            }
        }
    }

    public void enable(){
        enabled = true;
    }

    private void updateAccountsAndOrdersBasedOnReceivedDataPoint(DataPoint dataPoint){
        double value = dataPoint.getCloseValue();
        Iterable<Account> accounts = accountRepository.findAll();
        for(Account account : accounts){
            updateOrdersAndAccountBalance(account, value);
        }
    }

    private void updateOrdersAndAccountBalance(Account account, double value){
        List<Currency_Order> orders = account.getCurrencyOrders();
        for(Currency_Order order : orders){
            updateOrderAndAccountBalance(account,order, value);
        }
        accountRepository.save(account);
    }

    private void updateOrderAndAccountBalance(Account account,Currency_Order order, double value){
        double orderBalanceChange = computeOrderBalanceChange(order, value);
        updateOrderBalance(order, orderBalanceChange);
        updateAccountBalance(account, orderBalanceChange);
        closeOrderIfTakeProfitOrStopLossHit(order);
        orderRepository.save(order);
    }


    private double computeOrderBalanceChange(Currency_Order order, double newValue){
        double earlierValue = order.getProfit();
        int pipsChange = computePipsChangeOnLong(earlierValue, newValue);
        double lot = order.getLot();

        if(order.getShortLong()== ShortLong.LONG){
            return pipsChange * lot * 10;
        } else {
            return -pipsChange * lot * 10;
        }
    }

    private int computePipsChangeOnLong(double earlierValue, double newValue){
        double changeInValue = newValue - earlierValue;
        return (int) (changeInValue * 10000);
    }


    private void updateOrderBalance(Currency_Order order, double orderBalanceChange){
        double balanceToUpdate = order.getProfit();
        double newBalance = balanceToUpdate + orderBalanceChange;
        order.setProfit(newBalance);
    }

    private void updateAccountBalance(Account account, double orderBalancheChange){
        double balanceToUpdate = account.getBalance();
        double newBalance = balanceToUpdate + orderBalancheChange;
        account.setBalance(newBalance);
    }

    private void closeOrderIfTakeProfitOrStopLossHit(Currency_Order order){
        double balance = order.getProfit();
        double tpVal = order.getTpVal();
        double slVal = order.getSlVal();

        if(checkIfTakeProfitHit(balance, tpVal) || checkIfStopLossHit(balance, slVal)){
            orderClose(order);
        }
    }

    private boolean checkIfTakeProfitHit(double balance, double tpVal){
        if(tpVal > 0){
            if(balance > tpVal){
                return true;
            }
        } else {
            if(balance < tpVal){
                return true;
            }
        }
        return false;
    }

    private boolean checkIfStopLossHit(double balance, double slVal){
        if(slVal > 0){
            if(balance > slVal){
                return true;
            }
        } else {
            if(balance < slVal){
                return true;
            }
        }
        return false;
    }

    private void orderClose(Currency_Order order){
        order.setOrderState(OrderState.CLOSED);
    }
}
