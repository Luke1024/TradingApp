package app.main;

import app.main.domain.wrapper.CurrencyDataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataPrinter {

    @Autowired
    private ApiConnectorService apiConnectorService;

    @Scheduled(fixedRate = 200000)
    public void print(){
        Optional<CurrencyDataWrapper> currencyDataWrapper = apiConnectorService.getCompact();

        CurrencyDataWrapper wrapper = currencyDataWrapper.get();

        System.out.println(wrapper.getTimeSeries().toString());
    }
}
