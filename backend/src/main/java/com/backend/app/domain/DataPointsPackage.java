package com.backend.app.domain;

import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.wrapper.MetaData;

import java.util.List;
import java.util.Optional;

public class DataPointsPackage {
    private Optional<MetaData> metaData;
    private List<DataPoint> dataPoints;

    public DataPointsPackage(Optional<MetaData> metaData, List<DataPoint> dataPoints) {
        this.metaData = metaData;
        this.dataPoints = dataPoints;
    }

    public Optional<MetaData> getMetaData() {
        return metaData;
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }
}
