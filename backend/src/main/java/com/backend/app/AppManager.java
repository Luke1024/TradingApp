package com.backend.app;

import com.backend.app.service.UpdateWorker;
import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.InstrumentDataDownloaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppManager {

    @Autowired
    private InstrumentDataDownloaderService instrumentDataDownloaderService;

    @Autowired
    private UpdateWorker updateWorker;

    private Logger logger = LoggerFactory.getLogger(AppManager.class);

    public void executeAppOperations(){
        downloadTimeSeries();
        enableExchangeRateDownloading();
        enableUpdateWorker();
    }

    private void downloadTimeSeries(){
        instrumentDataDownloaderService.saveTimeSeries();
        logger.info("Saved timeseries.");
        logger.info(instrumentDataDownloaderService.getDataBaseInfo());
    }

    private void enableExchangeRateDownloading(){
        instrumentDataDownloaderService.enableExchangeRateDownloading();
        logger.info("Enabled exchange rate downloading.");
    }

    private void enableUpdateWorker(){
        logger.info("Enabled update worker.");
        updateWorker.enable();
    }
}
