package app.main.domain;

public class DataPointDto {
    double value;
    String name;

    public DataPointDto() {
    }

    public DataPointDto(double value, String name) {
        this.value = value;
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
