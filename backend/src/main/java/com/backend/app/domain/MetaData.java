package com.backend.app.domain;

import java.time.LocalDateTime;

public class MetaData {
    private String information;
    private String fromSymbol;
    private String toSymbol;
    private LocalDateTime lastRefreshed;
    private String interval;
    private String outputSize;
    private String timeZone;

    public MetaData(String information, String fromSymbol, String toSymbol, LocalDateTime lastRefreshed,
                    String interval, String outputSize, String timeZone) {
        this.information = information;
        this.fromSymbol = fromSymbol;
        this.toSymbol = toSymbol;
        this.lastRefreshed = lastRefreshed;
        this.interval = interval;
        this.outputSize = outputSize;
        this.timeZone = timeZone;
    }

    public String getInformation() {
        return information;
    }

    public String getFromSymbol() {
        return fromSymbol;
    }

    public String getToSymbol() {
        return toSymbol;
    }

    public LocalDateTime getLastRefreshed() {
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
