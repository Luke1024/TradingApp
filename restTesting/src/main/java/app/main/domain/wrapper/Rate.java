package app.main.domain.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class Rate {

    @JsonProperty("1. From_Currency Code")
    private String fromCurrencyCode;
    @JsonProperty(". To_Currency Code")
    private String toCurrencyCode;
    @JsonProperty("5. Exchange Rate")
    private String exchangeRate;
    @JsonProperty("6. Last Refreshed")
    private String lastRefreshed;
    @JsonProperty("7. Time Zone")
    private String timeZone;
    @JsonProperty("8. Bid Price")
    private String bidPrice;
    @JsonProperty("9. Ask Price")
    private String askPrice;

    public Rate() {
    }

    public String getFromCurrencyCode() {
        return fromCurrencyCode;
    }

    public String getToCurrencyCode() {
        return toCurrencyCode;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public String getLastRefreshed() {
        return lastRefreshed;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public String getAskPrice() {
        return askPrice;
    }
}
