package com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MetaDataWrapper {

    @JsonProperty("1. Information")
    private String information;
    @JsonProperty("2. From Symbol")
    private String fromSymbol;
    @JsonProperty("3. To Symbol")
    private String toSymbol;
    @JsonProperty("4. Last Refreshed")
    private String lastRefreshed;
    @JsonProperty("5. Interval")
    private String interval;
    @JsonProperty("6. Output Size")
    private String outputSize;
    @JsonProperty("7. Time Zone")
    private String timeZone;

    public String getInformation() {
        return information;
    }

    public String getFromSymbol() {
        return fromSymbol;
    }

    public String getToSymbol() {
        return toSymbol;
    }

    public String getLastRefreshed() {
        return lastRefreshed;
    }

    public String getInterval() {
        return interval;
    }

    public String getOutputSize() {
        return outputSize;
    }

    public String getTimeZone() {
        return timeZone;
    }
}
