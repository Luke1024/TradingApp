package com.backend.app.domain;

import java.time.LocalDateTime;

public class DataPoint {
    LocalDateTime timeStamp;
    double open;
    double high;
    double low;
    double close;
    double volume;

    public DataPoint(LocalDateTime timeStamp, double open, double high, double low,
                     double close, double volume) {
        this.timeStamp = timeStamp;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public double getVolume() {
        return volume;
    }
}
