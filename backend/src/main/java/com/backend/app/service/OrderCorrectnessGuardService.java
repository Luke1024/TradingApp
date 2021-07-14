package com.backend.app.service;

import com.backend.app.domain.dto.OrderDto;
import com.backend.app.domain.dto.OrderInfoDto;
import com.backend.app.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderCorrectnessGuardService {

    @Autowired
    private OrderRepository orderRepository;


    private Logger logger = LoggerFactory.getLogger(OrderCorrectnessGuardService.class);

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
        double lotMin = 0.1;
        double lotMax = 1;
        //when available lot is below minimal lot settings it should be blocking order opening
        if(lot < lotMin || lot > lotMax){
            return generateStringlotValueBetween(lotMin, lotMax);
        }else{
            return "";
        }
    }

    private String generateStringlotValueBetween(double lotMin, double lotMax){
        return "Problem with lot value";
    }

    private String analyzeTpPips(OrderDto orderDto){
        int tpPips = orderDto.getTpPips();
        int tpPipsMin = 1;
        int tpPipsMax = 1000;
        if(tpPips < tpPipsMin || tpPips > tpPipsMax){
            return generateStringTpPipsValueBetween(tpPipsMin, tpPipsMax);
        } else {
            return "";
        }
    }

    private String generateStringTpPipsValueBetween(int tpPipsMin, int tpPipsMax){
        return "Problem with tp pips value.";
    }

    private double computeTpVal(OrderDto orderDto){
        return orderDto.getTpPips() * 10;
    }

    private String analyzeSlPips(OrderDto orderDto){
        int slPips = orderDto.getSlPips();
        int slPipsMin = -1;
        int slPipsMax = -1000;
        if(slPips < slPipsMin || slPipsMax > slPipsMax){
            return genetateStringSlPipsValueBetween(slPipsMin, slPipsMax);
        } else {
            return "";
        }
    }

    private String genetateStringSlPipsValueBetween(int slPipsMin, int slPipsMax){
        return "Problem with sl pips value";
    }

    private double computeSlVal(OrderDto orderDto){
        return orderDto.getSlVal() * 10;
    }
}
