package com.backend.app.service.instrument.data.downloader.manager.service.downloader.service;

import com.backend.app.domain.DataPoint;
import com.backend.app.domain.DataPointsPackage;
import com.backend.app.domain.wrapper.CurrencyDataWrapper;
import com.backend.app.service.DataPointService;
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
    private DataPointService dataPointService;

    @Autowired
    private DataPointsRetrieverService dataPointsRetrieverService;

    @Autowired
    private ExchangeRateRetrieverService exchangeRateRetrieverService;


    public boolean saveTimeSeries(){
        Optional<DataPointsPackage> dataPointsPackage = dataPointsRetrieverService.getTimeSeriesData();

        if(dataPointsPackage.get()){

        }
    }



    public boolean getCurrentExchangeRate(){
        List<DataPoint> dataPoints = retrieveAndProcessPoints(b);
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
        return Optional.empty();
    }

    public boolean loadLast100DataPointsToDatabase() {
        List<DataPoint> dataPoints = retrieveAndProcessPoints(fxIntradayEndpoint);
        if(dataPoints.isEmpty()){
            return false;
        } else {
            savePoints(dataPoints);
            return true;
        }
    }

    private List<DataPoint> retrieveAndProcessPoints(String baseEndPoint) {
        Optional<CurrencyDataWrapper> dataWrapper = dataDownloaderService.downloadDataFromApi(baseEndPoint);
        if (dataWrapper.isPresent()) {
            return dataPointProcessorService.processPoints(dataWrapper.get().getTimeSeries());
        } else {
            return new ArrayList<>();
        }
    }

    private void savePoints(List<DataPoint> dataPoints) {
        dataPointService.saveDataPoints(dataPoints);
    }


}
