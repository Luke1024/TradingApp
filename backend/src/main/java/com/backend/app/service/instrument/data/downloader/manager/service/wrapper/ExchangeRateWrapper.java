package com.backend.app.service.instrument.data.downloader.manager.service.wrapper;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ExchangeRateWrapper {

    @JsonProperty("Realtime Currency Exchange Rate")
    private RateWrapper rateWrapper;

    public ExchangeRateWrapper() {
    }

    public RateWrapper getRateWrapper() {
        return rateWrapper;
    }

    @Override
    public String toString() {
        return "ExchangeRateWrapper{" +
                "rateWrapper=" + rateWrapper.toString() +
                '}';
    }
}
