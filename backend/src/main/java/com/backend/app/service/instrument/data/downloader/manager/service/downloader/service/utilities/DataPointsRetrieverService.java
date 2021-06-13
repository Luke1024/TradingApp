package com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.utilities;

import com.backend.app.domain.DataPoint;
import com.backend.app.domain.DataPointsPackage;
import com.backend.app.domain.MetaData;
import com.backend.app.domain.wrapper.CurrencyDataWrapper;
import com.backend.app.domain.wrapper.DataPointWrapper;
import com.backend.app.domain.wrapper.MetaDataWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DataPointsRetrieverService {

    @Autowired
    private ApiConnectorService connectorService;

    private Logger logger = LoggerFactory.getLogger(DataPointsRetrieverService.class);

    public Optional<DataPointsPackage> getTimeSeriesData(){
        Optional<CurrencyDataWrapper> currencyDataWrapper = connectorService.getM5();
        if(currencyDataWrapper.isPresent()) {
            return Optional.of(parseWrapper(currencyDataWrapper.get()));
        }
        return Optional.empty();
    }

    private DataPointsPackage parseWrapper(CurrencyDataWrapper currencyDataWrapper){
        Optional<MetaData> metaData = parseMetaData(currencyDataWrapper.getMetaDataWrapper());
        List<DataPoint> dataPoints = parsePoints(currencyDataWrapper.getTimeSeries());
        return new DataPointsPackage(metaData,dataPoints);
    }

    private Optional<MetaData> parseMetaData(MetaDataWrapper metaDataWrapper){
        try {
            return Optional.of(new MetaData(
                    metaDataWrapper.getInformation(),
                    metaDataWrapper.getFromSymbol(),
                    metaDataWrapper.getToSymbol(),
                    LocalDateTime.parse(metaDataWrapper.getLastRefreshed()),
                    metaDataWrapper.getInterval(),
                    metaDataWrapper.getOutputSize(),
                    metaDataWrapper.getTimeZone()));
        } catch (Exception e) {
            logger.warn("Failed to parse metadata. " + e.toString());
            return Optional.empty();
        }
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

    private Optional<DataPoint> parsePoint(String key, DataPointWrapper pointWrapper){
        try {
            return Optional.of(new DataPoint(
                    LocalDateTime.parse(key),
                    Double.parseDouble(pointWrapper.getClose())));
        } catch (Exception e){
            logger.warn("Failed to parse datapoint. " + e.toString());
            return Optional.empty();
        }
    }
}
