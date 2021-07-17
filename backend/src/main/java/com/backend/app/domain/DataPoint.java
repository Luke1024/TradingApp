package com.backend.app.domain;

import java.time.LocalDateTime;

public class DataPoint {

    private Long id;
    LocalDateTime timeStamp;
    double closeValue;

    public DataPoint() {
    }

    public DataPoint(LocalDateTime timeStamp, double closeValue) {
        this.timeStamp = timeStamp;
        this.closeValue = closeValue;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public double getCloseValue() {
        return closeValue;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "id=" + id +
                ", timeStamp=" + timeStamp +
                ", close=" + closeValue +
                '}';
    }
}
