package com.backend.app.domain.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class DataPointWrapper {

    @JsonProperty("1. open")
    private String open;
    @JsonProperty("2. high")
    private String high;
    @JsonProperty("3. low")
    private String low;
    @JsonProperty("4. close")
    private String close;
    @JsonProperty("5. volume")
    private String volume;

    public DataPointWrapper() {
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "open='" + open + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", close='" + close + '\'' +
                ", volume='" + volume + '\'' +
                '}';
    }
}
