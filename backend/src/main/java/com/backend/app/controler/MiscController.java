package com.backend.app.controler;

import com.backend.app.domain.dto.DataPointDto;
import com.backend.app.domain.dto.TradingStateDto;
import com.backend.app.mapper.DataPointMapper;
import com.backend.app.service.DataPointAndExchangeRateService;
import com.backend.app.service.MiscService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/trading")
public class MiscController {

    @Autowired
    private DataPointAndExchangeRateService dataPointAndExchangeRateService;

    @Autowired
    private DataPointMapper dataPointMapper;

    @Autowired
    private MiscService miscService;

    @GetMapping(value = "/currencydata")
    public List<DataPointDto> getData(){
        return dataPointMapper.mapToDtoList(dataPointAndExchangeRateService.get100DataPoints());
    }

    @GetMapping(value = "/token")
    public String getToken(){
        return miscService.getToken();
    }

    @GetMapping(value = "/getTradingState")
    public TradingStateDto getTradingState(){
        return miscService.getTradingState();
    }
}
