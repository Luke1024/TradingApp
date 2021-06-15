package com.backend.app.controler;

import com.backend.app.domain.DataPointDto;
import com.backend.app.mapper.DataPointMapper;
import com.backend.app.service.DataPointAndExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/trading")
public class CurrencyDataController {

    @Autowired
    private DataPointAndExchangeRateService dataPointAndExchangeRateService;

    @Autowired
    private DataPointMapper dataPointMapper;

    @GetMapping(value = "/currencydata")
    public List<DataPointDto> getData(){
        return dataPointMapper.mapToDtoList(dataPointAndExchangeRateService.get100DataPoints());
    }
}
