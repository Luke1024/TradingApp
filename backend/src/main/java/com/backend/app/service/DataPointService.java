package com.backend.app.service;

import com.backend.app.domain.DataPoint;
import com.backend.app.repository.DataPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataPointService {

    @Autowired
    private DataPointRepository pointRepository;

    private List<DataPoint> dataPoints = new ArrayList<>();

    public List<DataPoint> getDataPoints(){
        if(dataPoints.size()>100) {
            return dataPoints.subList(dataPoints.size() - 100, dataPoints.size());
        } else return dataPoints;
    }

    public void saveDataPoints(List<DataPoint> dataPoints){
        if(dataPoints != null){
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
        } else return false;
    }
}
