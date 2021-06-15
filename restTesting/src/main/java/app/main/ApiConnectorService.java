package app.main;

import app.main.domain.wrapper.CurrencyDataWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ApiConnectorService {

    private Logger logger = LoggerFactory.getLogger(ApiConnectorService.class);

    private RestTemplate template = new RestTemplate();

    private String fxIntradayEndpointCompact = "https://www.alphavantage.co/query?function=FX_INTRADAY&from_symbol=EUR&to_symbol=USD&interval=5min&apikey=FGW9LY1I2F0A4VIB";

    private String getFxIntradayEndpointFull = "https://www.alphavantage.co/query?function=FX_INTRADAY&from_symbol=EUR&to_symbol=USD&interval=5min&outputsize=full&apikey=FGW9LY1I2F0A4VIB";

    public ApiConnectorService() {
    }

    public Optional<CurrencyDataWrapper> getCompact(){
        return downloadDataFromApi(fxIntradayEndpointCompact);
    }

    public Optional<CurrencyDataWrapper> getFull(){
        return downloadDataFromApi(getFxIntradayEndpointFull);
    }

    private Optional<CurrencyDataWrapper> downloadDataFromApi(String endPoint){
        try {
            return Optional.of(template.getForObject(fxIntradayEndpointCompact,
                    CurrencyDataWrapper.class));
        } catch (Exception e){
            logger.warn("Parsing or API failed. " + e.toString());
            return Optional.empty();
        }
    }

    public ResponseEntity<String> testString(){
        return template.getForEntity(fxIntradayEndpointCompact, String.class);
    }
}
