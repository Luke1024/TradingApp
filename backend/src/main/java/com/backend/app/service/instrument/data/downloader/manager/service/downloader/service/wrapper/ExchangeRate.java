package com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.wrapper;

import java.time.LocalDateTime;

public class ExchangeRate {
    private String fromCurrencyCode;
    private String toCurrencyCode;
    private double rate;
    private LocalDateTime lastRefreshed;
    private String timeZone;
    private double bidPrice;
    private double askPrice;

    public ExchangeRate() {
    }

    public ExchangeRate(String fromCurrencyCode, String toCurrencyCode, double rate, LocalDateTime lastRefreshed,
                        String timeZone, double bidPrice, double askPrice) {
        this.fromCurrencyCode = fromCurrencyCode;
        this.toCurrencyCode = toCurrencyCode;
        this.rate = rate;
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

    public double getRate() {
        return rate;
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

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "fromCurrencyCode='" + fromCurrencyCode + '\'' +
                ", toCurrencyCode='" + toCurrencyCode + '\'' +
                ", exchangeRate=" + rate +
                ", lastRefreshed=" + lastRefreshed +
                ", timeZone='" + timeZone + '\'' +
                ", bidPrice=" + bidPrice +
                ", askPrice=" + askPrice +
                '}';
    }
}
