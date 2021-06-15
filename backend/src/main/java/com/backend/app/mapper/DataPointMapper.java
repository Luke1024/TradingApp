package com.backend.app.mapper;

import com.backend.app.domain.DataPoint;
import com.backend.app.domain.DataPointDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataPointMapper {
    public List<DataPointDto> mapToDtoList(List<DataPoint> dataPoints){
        List<DataPointDto> dataPointDtos = new ArrayList<>();

        for(DataPoint dataPoint : dataPoints){
            dataPointDtos.add(mapToDto(dataPoint));
        }
        return dataPointDtos;
    }

    public DataPointDto mapToDto(DataPoint dataPoint){
        return new DataPointDto(dataPoint.getClose(), dataPoint.getTimeStamp().toString());
    }
}
