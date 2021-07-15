package com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.utilities;

import com.backend.app.service.instrument.data.downloader.manager.service.wrapper.CurrencyDataWrapper;
import com.backend.app.service.instrument.data.downloader.manager.service.wrapper.ExchangeRateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ApiConnectorService {

    private Logger logger = LoggerFactory.getLogger(ApiConnectorService.class);

    private RestTemplate template = new RestTemplate();

    private String fxIntradayEndpointCompact = "https://www.alphavantage.co/query?function=FX_INTRADAY&from_symbol=EUR&to_symbol=USD&interval=5min&apikey=FGW9LY1I2F0A4VIB";

    private String fxIntradayEndpointFull = "https://www.alphavantage.co/query?function=FX_INTRADAY&from_symbol=EUR&to_symbol=USD&interval=5min&outputsize=full&apikey=FGW9LY1I2F0A4VIB";

    private String fxEchangeRate = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=EUR&to_currency=USD&apikey=FGW9LY1I2F0A4VIB";


    public ApiConnectorService() {
    }

    public Optional<CurrencyDataWrapper> getM5(){
        return downloadDataFromApi(fxIntradayEndpointCompact);
    }

    public Optional<ExchangeRateWrapper> getExchangeRate(){
        try {
            return Optional.of(template.getForObject(fxEchangeRate, ExchangeRateWrapper.class));
        } catch (Exception e){
            logger.warn("Parsing or API failed. " + e.toString());
            return Optional.empty();
        }
    }

    private Optional<CurrencyDataWrapper> downloadDataFromApi(String endPoint){
        try {
            return Optional.of(template.getForObject(endPoint,
                    CurrencyDataWrapper.class));
        } catch (Exception e){
            logger.warn("Parsing or API failed. " + e.toString());
            return Optional.empty();
        }
    }
}
