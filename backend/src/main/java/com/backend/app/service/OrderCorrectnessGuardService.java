package com.backend.app.service;

import com.backend.app.domain.dto.OrderDto;
import com.backend.app.domain.dto.OrderInfoDto;
import com.backend.app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderCorrectnessGuardService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLimitsComputingService orderLimitsComputingService;

    private static double lotMin = 0.01;

    public OrderInfoDto getInfo(OrderDto orderDto) {
        Optional<OrderLimitsComputingService.AvailableParameters> parameters =
                orderLimitsComputingService.computeOrderAvailableParameters(orderDto);

        if (parameters.isPresent()) {
            return processWithAnalysis(orderDto, parameters.get());
        } else return new OrderInfoDto(false);
    }

    private OrderInfoDto processWithAnalysis(OrderDto orderDto, OrderLimitsComputingService.AvailableParameters parameters){

        String lotInfo = analyzeLot(orderDto, parameters);
        String tpPipsInfo = analyzeTpPips(orderDto, parameters);
        double tpVal = computeTpVal(orderDto, parameters);
        String slPipsInfo = analyzeSlPips(orderDto, parameters);
        double slVal = computeSlVal(orderDto, parameters);

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

    private String analyzeLot(OrderDto orderDto, OrderLimitsComputingService.AvailableParameters parameters){
        double lot = orderDto.getLot();
        double lotMin = parameters.lotMin;
        double lotMax = parameters.lotMax;
        //when available lot is below minimal lot settings it should be blocking order opening
        if(lot < lotMin || lot > lotMax){
            return generateStringlotValueBetween(lotMin, lotMax);
        }else{
            return "";
        }
    }

    private String generateStringlotValueBetween(double lotMin, double lotMax){
        return "";
    }

    private String analyzeTpPips(OrderDto orderDto, OrderLimitsComputingService.AvailableParameters parameters){
        int tpPips = orderDto.getTpPips();
        int tpPipsMin = parameters.tpPipsMin;
        int tpPipsMax = parameters.tpPipsMax;
        if(tpPips < tpPipsMin || tpPips > tpPipsMax){
            return generateStringTpPipsValueBetween(tpPipsMin, tpPipsMax);
        } else {
            return "";
        }
    }

    private String generateStringTpPipsValueBetween(int tpPipsMin, int tpPipsMax){
        return "";
    }

    private double computeTpVal(OrderDto orderDto, OrderLimitsComputingService.AvailableParameters parameters){
        return orderDto.getTpPips() * parameters.valMultiplier;
    }

    private String analyzeSlPips(OrderDto orderDto, OrderLimitsComputingService.AvailableParameters parameters){
        int slPips = orderDto.getSlPips();
        int slPipsMin = parameters.slPipsMin;
        int slPipsMax = parameters.slPipsMax;
        if(slPips < slPipsMin || slPipsMax > slPipsMax){
            return genetateStringSlPipsValueBetween(slPipsMin, slPipsMax);
        } else {
            return "";
        }
    }

    private String genetateStringSlPipsValueBetween(int slPipsMin, int slPipsMax){
        return "";
    }

    private double computeSlVal(OrderDto orderDto, OrderLimitsComputingService.AvailableParameters parameters){
        return orderDto.getSlVal() * parameters.valMultiplier;
    }
}
