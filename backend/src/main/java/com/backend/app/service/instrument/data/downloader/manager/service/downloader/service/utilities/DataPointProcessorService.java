package com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.utilities;

import com.backend.app.domain.DataPoint;
import com.backend.app.domain.wrapper.DataPointWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DataPointProcessorService {

    private Logger logger = LoggerFactory.getLogger(DataPointProcessorService.class);

    public List<DataPoint> processPoints(Map<String,DataPointWrapper> dataPointWrappers) {
        return parsePoints(dataPointWrappers);
    }

    private List<DataPoint> parsePoints(Map<String, DataPointWrapper> timeSeries){
        List<DataPoint> dataPointsParsed = new ArrayList<>();
        for(String key : timeSeries.keySet()) {
            Optional<DataPoint> dataPointOptional = parsePoint(key, timeSeries.get(key));
            if (dataPointOptional.isPresent()) {
                dataPointsParsed.add(dataPointOptional.get());
            }
        }
        return dataPointsParsed;
    }

    public Optional<DataPoint> parsePoint(String key, DataPointWrapper pointWrapper){
        try {
            return Optional.of(new DataPoint(
                    LocalDateTime.parse(key),
                    Double.parseDouble(pointWrapper.getOpen()),
                    Double.parseDouble(pointWrapper.getHigh()),
                    Double.parseDouble(pointWrapper.getLow()),
                    Double.parseDouble(pointWrapper.getClose()),
                    Double.parseDouble(pointWrapper.getVolume())));
        } catch (Exception e){
            logger.warn("Failed to parse datapoint.");
            return Optional.empty();
        }
    }
}
