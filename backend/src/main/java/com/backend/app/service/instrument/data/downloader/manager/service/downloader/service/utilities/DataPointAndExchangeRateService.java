package com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.utilities;

import com.backend.app.domain.DataPoint;
import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.wrapper.ExchangeRate;
import com.backend.app.repository.DataPointMockedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class DataPointAndExchangeRateService {

    @Autowired
    private DataPointMockedRepository dataPointMockedRepository;

    public List<DataPoint> get100DataPoints(){
        List<DataPoint> dataPoints = dataPointMockedRepository.getAllDataPoints();

        if(dataPoints.size()>=100) {
            return dataPoints.subList(dataPoints.size() - 100, dataPoints.size());
        } else return dataPoints;
    }

    public List<DataPoint> getAllDataPoints() {
        return dataPointMockedRepository.getAllDataPoints();
    }

    public void saveDataPointAsExchangeRateIn(ExchangeRate exchangeRate){
        DataPoint dataPointFromRate = new DataPoint(
                exchangeRate.getLastRefreshed(),
                exchangeRate.getRate());
        saveDataPoint(dataPointFromRate);
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
                dataPointMockedRepository.save(dataPoint);
        }
    }
}
