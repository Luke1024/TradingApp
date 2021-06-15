package com.backend.app.service.instrument.data.downloader.manager.service.downloader.service;

import com.backend.app.domain.DataPoint;
import com.backend.app.domain.DataPointsPackage;
import com.backend.app.domain.ExchangeRate;
import com.backend.app.service.DataPointAndExchangeRateService;
import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.utilities.DataPointsRetrieverService;
import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.utilities.ExchangeRateRetrieverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InstrumentDataDownloaderService {

    private Logger logger = LoggerFactory.getLogger(InstrumentDataDownloaderService.class);


    @Autowired
    private DataPointAndExchangeRateService dataPointAndExchangeRateService;

    @Autowired
    private DataPointsRetrieverService dataPointsRetrieverService;

    @Autowired
    private ExchangeRateRetrieverService exchangeRateRetrieverService;

    public boolean saveTimeSeries(){
        Optional<DataPointsPackage> dataPointsPackage = dataPointsRetrieverService.getTimeSeriesData();

        if(dataPointsPackage.isPresent()){
            if(dataPointsPackage.get().getDataPoints() != null){
                if( ! dataPointsPackage.get().getDataPoints().isEmpty()){
                    savePoints(dataPointsPackage.get().getDataPoints());
                    return true;
                }
            }
        }
        return false;
    }

    public boolean saveCurrentExchangeRate(){
        Optional<ExchangeRate> exchangeRate = exchangeRateRetrieverService.getCurrentExchangeRate();
        if(exchangeRate.isPresent()){
            saveRate(exchangeRate.get());
            return true;
        } else return false;
    }

    private void savePoints(List<DataPoint> dataPoints) {
        dataPointAndExchangeRateService.saveDataPoints(dataPoints);
        logger.info("Saved points: " + dataPointAndExchangeRateService.getAllDataPoints().size());
    }

    private void saveRate(ExchangeRate rate){
        dataPointAndExchangeRateService.saveDataPointAsExchangeRateIn(rate);
    }
}
