package com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.wrapper;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ExchangeRateWrapper {

    @JsonProperty("Realtime Currency Exchange Rate")
    private RateWrapper rateWrapper;

    public RateWrapper getRateWrapper() {
        return rateWrapper;
    }

    public ExchangeRateWrapper() {
    }

    @Override
    public String toString() {
        return "ExchangeRateWrapper{" +
                "rateWrapper=" + rateWrapper.toString() +
                '}';
    }
}
