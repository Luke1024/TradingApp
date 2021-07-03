package com.backend.app.service;

import com.backend.app.domain.State;
import com.backend.app.domain.dto.OrderDto;
import com.backend.app.domain.entity.Currency_Order;
import org.springframework.stereotype.Service;

@Service
public class OrderModdingService {

    public Currency_Order mod(Currency_Order orderFromDatabase, OrderDto orderDto){
        State dtoState = orderDto.getState();
        State orderState = orderFromDatabase.getState();

        if(dtoState==State.EDIT || dtoState==State.OPEN){
            return mapSettingsAvailableInEditAndOpenMode(orderFromDatabase, orderDto);
        }else  if(dtoState==State.CREATION && dtoState==State.CREATION){
            return mapSettingsAvailableInCreationMode(orderFromDatabase, orderDto);
        }
        return orderFromDatabase;
    }

    private Currency_Order mapSettingsAvailableInEditAndOpenMode(Currency_Order orderFromDatabase, OrderDto orderDto){
        orderFromDatabase.setTpPips(orderDto.getTpPips());
        orderFromDatabase.setSlPips(orderDto.getSlPips());
        orderFromDatabase.setState(orderDto.getState());
        return orderFromDatabase;
    }

    private Currency_Order mapSettingsAvailableInCreationMode(Currency_Order orderFromDatabase, OrderDto orderDto){
        orderFromDatabase.setLot(orderDto.getLot());
        orderFromDatabase.setTpPips(orderDto.getTpPips());
        orderFromDatabase.setTpVal(orderDto.getTpVal());
        orderFromDatabase.setSlPips(orderDto.getSlPips());
        orderFromDatabase.setSlVal(orderDto.getSlVal());
        orderFromDatabase.setProfit(orderDto.getProfit());
        orderFromDatabase.setState(orderDto.getState());
        return orderFromDatabase;
    }
}
