package com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.wrapper;

import java.time.LocalDateTime;

public class ExchangeRate {
    private String fromCurrencyCode;
    private String toCurrencyCode;
    private double exchangeRate;
    private LocalDateTime lastRefreshed;
    private String timeZone;
    private double bidPrice;
    private double askPrice;

    public ExchangeRate(String fromCurrencyCode, String toCurrencyCode, double exchangeRate, LocalDateTime lastRefreshed,
                        String timeZone, double bidPrice, double askPrice) {
        this.fromCurrencyCode = fromCurrencyCode;
        this.toCurrencyCode = toCurrencyCode;
        this.exchangeRate = exchangeRate;
        this.lastRefreshed = lastRefreshed;
        this.timeZone = timeZone;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
    }

    public String getFromCurrencyCode() {
        return fromCurrencyCode;
    }

    public String getToCurrencyCode() {
        return toCurrencyCode;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public LocalDateTime getLastRefreshed() {
        return lastRefreshed;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public double getAskPrice() {
        return askPrice;
    }
}
