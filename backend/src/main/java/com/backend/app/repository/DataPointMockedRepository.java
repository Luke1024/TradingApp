package com.backend.app.repository;

import com.backend.app.domain.DataPoint;
import com.backend.app.service.UpdateWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataPointMockedRepository {

    private Logger logger = LoggerFactory.getLogger(DataPointMockedRepository.class);

    private List<DataPoint> dataPoints = new ArrayList<>();

    @Autowired
    private UpdateWorker updateWorker;

    public List<DataPoint> getAllDataPoints(){
        return dataPoints;
    }

    //allowing only later timestamp than earlier
    public void save(DataPoint dataPoint){
        if(isTimestampNew(dataPoint)) {
            dataPoints.add(dataPoint);
            sendValueChangeToUpdateWorker();
        }
    }

    private void sendValueChangeToUpdateWorker(){
        if(dataPoints.size()>1) {
            DataPoint lastPoint = dataPoints.get(dataPoints.size() - 1);
            DataPoint earlierPoint = dataPoints.get(dataPoints.size() - 2);
            double valueChange = lastPoint.getCloseValue() - earlierPoint.getCloseValue();
            updateWorker.updateChangeReceived(valueChange);
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
            if( ! lastTimeStamp.isEqual(newTimeStamp)) {
                return true;
            } else {
                logger.warn("New dataPoint is equal to earlier timestamp.");
            }
        } else {
            logger.warn("New dataPoint has earlier timestamp.");
        }
        return false;
    }
}
