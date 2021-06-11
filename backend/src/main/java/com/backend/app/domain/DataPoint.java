package com.backend.app.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class DataPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    LocalDateTime timeStamp;
    double open;
    double high;
    double low;
    double close;
    double volume;

    public DataPoint() {
    }

    public DataPoint(LocalDateTime timeStamp, double open, double high, double low,
                     double close, double volume) {
        this.timeStamp = timeStamp;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public Long getId() {
        return id;
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
