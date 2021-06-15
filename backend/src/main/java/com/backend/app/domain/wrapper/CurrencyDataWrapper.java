package com.backend.app.domain.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class CurrencyDataWrapper {

    @JsonProperty("Meta Data")
    private MetaDataWrapper metaDataWrapper;
    @JsonProperty("Time Series FX (5min)")
    private Map<String, DataPointWrapper> timeSeries;

    public CurrencyDataWrapper() {
    }

    public MetaDataWrapper getMetaDataWrapper() {
        return metaDataWrapper;
    }

    public Map<String, DataPointWrapper> getTimeSeries() {
        return timeSeries;
    }

    @Override
    public String toString() {
        return "CurrencyDataWrapper{" +
                "metaDataWrapper=" + metaDataWrapper.toString() +
                ", timeSeries=" + timeSeries.toString() +
                '}';
    }
}
