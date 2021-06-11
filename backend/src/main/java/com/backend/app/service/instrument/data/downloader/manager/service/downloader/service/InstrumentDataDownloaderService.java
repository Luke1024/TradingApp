package com.backend.app.service.instrument.data.downloader.manager.service.downloader.service;

import com.backend.app.domain.DataPoint;
import com.backend.app.domain.wrapper.CurrencyDataWrapper;
import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.utilities.DataDownloaderService;
import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.utilities.DataPointProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InstrumentDataDownloaderService {

    private Logger logger = LoggerFactory.getLogger(InstrumentDataDownloaderService.class);

    private String baseEndPoint = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&" +
            "symbol=TSLA&interval=5min&apikey=FGW9LY1I2F0A4VIB";

    @Autowired
    private DataDownloaderService dataDownloaderService;

    @Autowired
    private DataPointProcessorService dataPointProcessorService;
/*
    public boolean loadLastDataPointToDatabase(){
        List<DataPoint> dataPoints = retrieveAndProcessPoints(baseEndPoint);
        if(dataPoints.isEmpty()){
            return false;
        } else {
            Optional<DataPoint> dataPointOptional = getLastPoint(dataPoints);
            if(dataPointOptional.isPresent()){
                //savePoints(Arrays.asList(dataPointOptional.get()));
                return true;
            }
        }
        return false;
    }

    private Optional<DataPoint> getLastPoint(List<DataPoint> dataPoints){

    }

    public boolean loadLast100DataPointsToDatabase(String baseEndPoint) {
        List<DataPoint> dataPoints = retrieveAndProcessPoints(baseEndPoint);
        if(dataPoints.isEmpty()){
            return false;
        } else {
            //savePoints(dataPoints);
            return true;
        }
    }

    public List<DataPoint> retrieveAndProcessPoints(String baseEndPoint) {
        Optional<CurrencyDataWrapper> dataWrapper = dataDownloaderService.downloadDataFromApi(baseEndPoint);
        if (dataWrapper.isPresent()) {
            return dataPointProcessorService.processPoints(dataWrapper.get().getTimeSeries());
        } else {
            return new ArrayList<>();
        }
    }

*/








}
