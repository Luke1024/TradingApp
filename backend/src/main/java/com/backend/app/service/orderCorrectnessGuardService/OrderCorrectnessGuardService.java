package com.backend.app.service.orderCorrectnessGuardService;

import com.backend.app.domain.dto.OrderDto;
import com.backend.app.domain.dto.OrderInfoDto;
import com.backend.app.repository.OrderRepository;
import com.backend.app.service.orderCorrectnessGuardService.utils.PipsAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderCorrectnessGuardService {

    @Autowired
    private OrderRepository orderRepository;


    private Logger logger = LoggerFactory.getLogger(OrderCorrectnessGuardService.class);

    @Autowired
    private PipsAnalyzer pipsAnalyzer;

    private static double lotMin = 0.01;

    public OrderInfoDto getInfo(OrderDto orderDto) {
        String lotInfo = analyzeLot(orderDto);
        String tpPipsInfo = analyzeTpPips(orderDto);
        double tpVal = computeTpVal(orderDto);
        String slPipsInfo = analyzeSlPips(orderDto);
        double slVal = computeSlVal(orderDto);

        if(isStringBiggerThanZero(lotInfo, tpPipsInfo, slPipsInfo)){
            return new OrderInfoDto(lotInfo, tpPipsInfo, tpVal, slPipsInfo, slVal, false);
        } else {
            return new OrderInfoDto(lotInfo, tpPipsInfo, tpVal, slPipsInfo, slVal, true);
        }
    }

    private boolean isStringBiggerThanZero(String lotInfo, String tpPipsInfo, String slPipsInfo){
        if(lotInfo.length()>0 || tpPipsInfo.length()>0 || slPipsInfo.length()>0) return true;
        else return false;
    }

    private String analyzeLot(OrderDto orderDto){
        double lot = orderDto.getLot();
        //when available lot is below minimal lot settings it should be blocking order opening
        if(lot < lotMin){
            return "Lot value can't be lower than " + lotMin;
        }else{
            return "";
        }
    }

    private String analyzeTpPips(OrderDto orderDto){
        return pipsAnalyzer.analyzeTpPips(orderDto);
    }

    private double computeTpVal(OrderDto orderDto){
        int tpPips = orderDto.getTpPips();
        double lot = orderDto.getLot();
        return computeProfit(tpPips, lot);
    }

    private String analyzeSlPips(OrderDto orderDto){
        return pipsAnalyzer.analyzeSlPips(orderDto);
    }

    private double computeSlVal(OrderDto orderDto){
        int slPips = orderDto.getSlPips();
        double lot = orderDto.getLot();
        return computeProfit(slPips, lot);
    }

    private double computeProfit(int pips, double lot){
        return pips * lot * 10;
    }
}
