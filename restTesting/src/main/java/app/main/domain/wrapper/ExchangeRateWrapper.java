package app.main.domain.wrapper;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ExchangeRateWrapper {

    @JsonProperty("ealtime Currency Exchange Rate")
    private Rate rate;

    public ExchangeRateWrapper() {
    }

    public Rate getRate() {
        return rate;
    }
}
