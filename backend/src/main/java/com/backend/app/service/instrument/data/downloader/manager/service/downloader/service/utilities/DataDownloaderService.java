package com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.utilities;

import com.backend.app.domain.wrapper.CurrencyDataWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class DataDownloaderService {
    private Logger logger = LoggerFactory.getLogger(DataDownloaderService.class);
    private RestTemplate template = new RestTemplate();

    public Optional<CurrencyDataWrapper> downloadDataFromApi(String endPoint){
        try {
            URI url = UriComponentsBuilder.fromHttpUrl(
                    endPoint).build().encode().toUri();
            return Optional.of(template.getForObject(url, CurrencyDataWrapper.class));
        } catch (Exception e){
            logger.warn("Parsing or API failed.");
            return Optional.empty();
        }
    }
}
