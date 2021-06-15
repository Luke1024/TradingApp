package app.main.domain;

import java.time.LocalDateTime;


public class DataPoint {

    private Long id;
    LocalDateTime timeStamp;
    double close;

    public DataPoint() {
    }

    public DataPoint(LocalDateTime timeStamp, double close) {
        this.timeStamp = timeStamp;
        this.close = close;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public double getClose() {
        return close;
    }
}
