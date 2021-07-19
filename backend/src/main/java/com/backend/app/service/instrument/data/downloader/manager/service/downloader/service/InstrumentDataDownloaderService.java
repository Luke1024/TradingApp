package com.backend.app.service.instrument.data.downloader.manager.service.downloader.service;

import com.backend.app.domain.DataPoint;
import com.backend.app.domain.DataPointsPackage;
import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.wrapper.ExchangeRate;
import com.backend.app.repository.DataPointMockedRepository;
import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.utilities.DataPointAndExchangeRateService;
import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.utilities.DataPointsRetrieverService;
import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.utilities.ExchangeRateRetrieverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    private DataPointMockedRepository dataPointMockedRepository;

    private boolean enableExchangeRateDownloading = false;

    public void saveTimeSeries(){
        Optional<DataPointsPackage> dataPointsPackage = dataPointsRetrieverService.getTimeSeriesData();

        if(dataPointsPackage.isPresent()){
            if(dataPointsPackage.get().getDataPoints() != null){
                if( ! dataPointsPackage.get().getDataPoints().isEmpty()){
                    savePoints(dataPointsPackage.get().getDataPoints());
                } else {
                    logger.warn("Downloaded timeseries is empty.");
                }
            } else {
                logger.warn("Arraylist with datapoints is empty.");
            }
        } else {
            logger.warn("There is problem with downloading timeseries package.");
        }
    }

    public void enableExchangeRateDownloading(){
        enableExchangeRateDownloading = true;
    }

    public String getDataBaseInfo(){
        int databaseSize = dataPointMockedRepository.getAllDataPoints().size();
        DataPoint lastDataPoint = dataPointMockedRepository.getAllDataPoints().get(databaseSize-1);
        LocalDateTime lastTimeStamp = lastDataPoint.getTimeStamp();
        return "In database there is " + databaseSize + " datapoints. Last timestamp is from " + lastTimeStamp;
    }

    @Scheduled(cron ="0 0/5 * * * *")
    private void saveCurrentExchangeRate(){
        if(enableExchangeRateDownloading){
            Optional<ExchangeRate> exchangeRate = exchangeRateRetrieverService.getCurrentExchangeRate();
            if(exchangeRate.isPresent()) {
                saveRate(exchangeRate.get());
                logger.info("Current exchange rate: " + exchangeRate.get().toString());
            } else {
                logger.info("Problem with saving exchange rate.");
            }
        }
    }

    private void savePoints(List<DataPoint> dataPoints) {
        dataPointAndExchangeRateService.saveDataPoints(dataPoints);
    }

    private void saveRate(ExchangeRate rate){
        dataPointAndExchangeRateService.saveDataPointAsExchangeRateIn(rate);
    }
}
