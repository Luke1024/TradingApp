package com.backend.app.service.instrument.data.downloader.manager.service;

import com.backend.app.domain.DataPoint;
import com.backend.app.domain.ExchangeRate;
import com.backend.app.repository.DataPointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DataPointAndExchangeRateService {

    @Autowired
    private DataPointRepository pointRepository;

    private Logger logger = LoggerFactory.getLogger(DataPointAndExchangeRateService.class);

    private List<DataPoint> dataPoints = new ArrayList<>();

    private ExchangeRate currentExchangeRate = null;

    public List<DataPoint> get100DataPoints(){
        if(dataPoints.size()>=100) {
            return dataPoints.subList(dataPoints.size() - 100, dataPoints.size());
        } else return dataPoints;
    }

    public List<DataPoint> getAllDataPoints() {
        return dataPoints;
    }

    public Optional<ExchangeRate> getCurrentExchangeRate(){
        if(currentExchangeRate != null){
            return Optional.of(currentExchangeRate);
        } else {
            return Optional.empty();
        }
    }

    public void saveDataPointAsExchangeRateIn(ExchangeRate exchangeRate){
        if(exchangeRate != null){
            currentExchangeRate = exchangeRate;
            DataPoint dataPointFromRate = new DataPoint(
                    exchangeRate.getLastRefreshed(),
                    exchangeRate.getExchangeRate());
            saveDataPoint(dataPointFromRate);
        }
    }

    public void saveDataPoints(List<DataPoint> dataPoints){
        if(dataPoints != null){
            Collections.reverse(dataPoints);
            for(DataPoint dataPoint : dataPoints){
                saveDataPoint(dataPoint);
            }
        }
    }




    private void saveDataPoint(DataPoint dataPoint){
        if(dataPoint != null) {
            if(isTimestampNew(dataPoint)){
                dataPoints.add(dataPoint);
            }
        }
    }

    private boolean isTimestampNew(DataPoint dataPoint){
        if(dataPoints.isEmpty()) {
            return true;
        } else {
            if(compareTimeStamps(dataPoint)) {
                return true;
            }
        }
        return false;
    }

    private boolean compareTimeStamps(DataPoint dataPoint){
        LocalDateTime lastTimeStamp = dataPoints.get(dataPoints.size()-1).getTimeStamp();
        LocalDateTime newTimeStamp = dataPoint.getTimeStamp();
        if(lastTimeStamp.isBefore(newTimeStamp)){
            return true;
        } else {
            logger.warn(("New dataPoint has earlier timestamp."));
            return false;
        }
    }
}
