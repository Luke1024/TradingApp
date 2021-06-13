package com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.utilities;

import com.backend.app.domain.ExchangeRate;
import com.backend.app.domain.wrapper.ExchangeRateWrapper;
import com.backend.app.domain.wrapper.RateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ExchangeRateRetrieverService {

    @Autowired
    private ApiConnectorService connectorService;

    private Logger logger = LoggerFactory.getLogger(ExchangeRateRetrieverService.class);

    public Optional<ExchangeRate> getCurrentExchangeRate(){
        Optional<ExchangeRateWrapper> exchangeRateWrapper = connectorService.getExchangeRate();
        if(exchangeRateWrapper.isPresent()){
            return parseWrapper(exchangeRateWrapper.get().getRateWrapper());
        } else {
            return Optional.empty();
        }
    }

    private Optional<ExchangeRate> parseWrapper(RateWrapper rateWrapper){
        try {
            return Optional.of(new ExchangeRate(
                    rateWrapper.getFromCurrencyCode(),
                    rateWrapper.getToCurrencyCode(),
                    Double.parseDouble(rateWrapper.getExchangeRate()),
                    LocalDateTime.parse(rateWrapper.getLastRefreshed()),
                    rateWrapper.getTimeZone(),
                    Double.parseDouble(rateWrapper.getBidPrice()),
                    Double.parseDouble(rateWrapper.getAskPrice())));
        } catch (Exception e){
            logger.warn("Problem with parsing exchange wrapper. " + e.toString());
            return Optional.empty();
        }
    }
}
