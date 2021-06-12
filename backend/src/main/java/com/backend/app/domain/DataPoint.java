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
