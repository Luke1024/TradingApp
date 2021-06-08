package com.backend.app.domain.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties
public class CurrencyDataWrapper {

    @JsonProperty("Meta Data")
    private MetaDataWrapper metaDataWrapper;
    @JsonProperty("Time Series (5min)")
    private Map<String, DataPointWrapper> timeSeries;

    public CurrencyDataWrapper() {
    }

    public MetaDataWrapper getMetaDataWrapper() {
        return metaDataWrapper;
    }

    public void setMetaDataWrapper(MetaDataWrapper metaDataWrapper) {
        this.metaDataWrapper = metaDataWrapper;
    }

    public Map<String, DataPointWrapper> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(Map<String, DataPointWrapper> timeSeries) {
        this.timeSeries = timeSeries;
    }

    @Override
    public String toString() {
        return "TimeSeriesWrapper{" +
                "metaData=" + metaDataWrapper.toString() +
                ", timeSeries=" + timeSeries.toString() +
                '}';
    }
}
