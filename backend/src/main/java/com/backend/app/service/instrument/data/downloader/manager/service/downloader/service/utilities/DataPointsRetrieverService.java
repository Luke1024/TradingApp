package com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.utilities;

import com.backend.app.domain.DataPoint;
import com.backend.app.domain.DataPointsPackage;
import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.wrapper.MetaData;
import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.wrapper.CurrencyDataWrapper;
import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.wrapper.DataPointWrapper;
import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.wrapper.MetaDataWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            return parseWrapper(currencyDataWrapper.get());
        }
        return Optional.empty();
    }

    private Optional<DataPointsPackage> parseWrapper(CurrencyDataWrapper currencyDataWrapper){
        if(currencyDataWrapper != null) {
            Optional<MetaData> metaData = parseMetaData(currencyDataWrapper.getMetaDataWrapper());
            List<DataPoint> dataPoints = parsePoints(currencyDataWrapper.getTimeSeries());
            return Optional.of(new DataPointsPackage(metaData, dataPoints));
        } else {
            return Optional.empty();
        }
    }

    private Optional<MetaData> parseMetaData(MetaDataWrapper metaDataWrapper){
        try {
            return Optional.of(new MetaData(
                    metaDataWrapper.getInformation(),
                    metaDataWrapper.getFromSymbol(),
                    metaDataWrapper.getToSymbol(),
                    LocalDateTime.parse(metaDataWrapper.getLastRefreshed(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
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
                    LocalDateTime.parse(key, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    Double.parseDouble(pointWrapper.getClose())));
        } catch (Exception e){
            logger.warn("Failed to parse datapoint. " + e.toString());
            return Optional.empty();
        }
    }
}
