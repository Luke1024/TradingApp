package app.main;

import app.main.domain.DataPoint;
import app.main.domain.DataPointsPackage;
import app.main.domain.MetaData;
import app.main.domain.wrapper.CurrencyDataWrapper;
import app.main.domain.wrapper.DataPointWrapper;
import app.main.domain.wrapper.MetaDataWrapper;
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
        Optional<CurrencyDataWrapper> currencyDataWrapper = connectorService.getCompact();
        if(currencyDataWrapper.isPresent()) {
            return Optional.of(parseWrapper(currencyDataWrapper.get()));
        }
        return Optional.empty();
    }

    private DataPointsPackage parseWrapper(CurrencyDataWrapper currencyDataWrapper){
        Optional<MetaData> metaData = Optional.empty(); //parseMetaData(currencyDataWrapper.getMetaDataWrapper());
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
            logger.warn("Failed to parse metadata.");
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
            logger.warn("Failed to parse datapoint.");
            return Optional.empty();
        }
    }
}
