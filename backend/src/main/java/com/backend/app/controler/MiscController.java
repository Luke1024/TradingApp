package com.backend.app.controler;

import com.backend.app.domain.dto.DataPointDto;
import com.backend.app.domain.dto.StringDto;
import com.backend.app.mapper.DataPointMapper;
import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.utilities.DataPointAndExchangeRateService;
import com.backend.app.service.MiscService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://ec2-3-73-59-212.eu-central-1.compute.amazonaws.com/")
@CrossOrigin(origins = "*")
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
    public StringDto getToken(){
        return miscService.getToken();
    }
}
