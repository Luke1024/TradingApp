package com.backend.app.service.order.orderCorrectnessGuardService.utils;

import com.backend.app.domain.dto.OrderDto;
import org.springframework.stereotype.Component;

@Component
public class PipsAnalyzer {

    public String analyzeTpPips(OrderDto orderDto){
        if(orderDto.isLongOrder()){
            return tpPipsMustBeOnPlus(orderDto);
        } else {
            return tpPipsMustBeOnMinus(orderDto);
        }
    }

    private String tpPipsMustBeOnPlus(OrderDto orderDto){
        int tpPips = orderDto.getTpPips();
        if(tpPips >= 0){
            return "";
        } else {
            return "Take profit pips value must be 0 or above.";
        }
    }

    private String tpPipsMustBeOnMinus(OrderDto orderDto){
        int tpPips = orderDto.getTpPips();
        if(tpPips <= 0) {
            return "";
        } else {
            return "Take profit pips value must be 0 or below.";
        }
    }

    public String analyzeSlPips(OrderDto orderDto){
        if(orderDto.isLongOrder()){
            return slPipsMustBeOnMinus(orderDto);
        } else {
            return slPipsMustBeOnPlus(orderDto);
        }
    }

    private String slPipsMustBeOnMinus(OrderDto orderDto){
        int slPips = orderDto.getSlPips();
        if(slPips <= 0){
            return "";
        } else {
            return "Stop loss pips value must be 0 or below.";
        }
    }

    private String slPipsMustBeOnPlus(OrderDto orderDto){
        int slPips = orderDto.getSlPips();
        if(slPips >= 0){
            return "";
        } else {
            return "Stop loss pips value must be 0 or above.";
        }
    }
}
