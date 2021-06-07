package backend.app.domain.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties
public class TimeSeriesWrapper {

    @JsonProperty("Meta Data")
    private MetaData metaData;
    @JsonProperty("Time Series (5min)")
    private Map<String,DataPoint> timeSeries;

    public TimeSeriesWrapper() {
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public Map<String, DataPoint> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(Map<String, DataPoint> timeSeries) {
        this.timeSeries = timeSeries;
    }

    @Override
    public String toString() {
        return "TimeSeriesWrapper{" +
                "metaData=" + metaData.toString() +
                ", timeSeries=" + timeSeries.toString() +
                '}';
    }
}
