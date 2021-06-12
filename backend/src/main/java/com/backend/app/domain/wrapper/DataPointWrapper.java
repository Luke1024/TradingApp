package com.backend.app.domain.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class DataPointWrapper {

    @JsonProperty("4. close")
    private String close;

    public DataPointWrapper() {
    }


    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    @Override
    public String toString() {
        return "DataPointWrapper{" +
                "close='" + close + '\'' +
                '}';
    }
}
