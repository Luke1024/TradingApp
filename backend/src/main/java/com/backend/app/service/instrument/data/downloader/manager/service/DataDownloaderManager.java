package com.backend.app.service.instrument.data.downloader.manager.service;

import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.InstrumentDataDownloaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataDownloaderManager {

    @Autowired
    private InstrumentDataDownloaderService dataDownloaderService;

    private Logger logger = LoggerFactory.getLogger(DataDownloaderManager.class);

    private boolean enableCollectingSinglePointData;

    public DataDownloaderManager() {
        this.enableCollectingSinglePointData = false;
    }

    //@Scheduled(fixedRate = 300000)
    private void downloadStartingData(){
        if(enableCollectingSinglePointData) {
            if(downloadCurrentExchangeRate()){
                logger.info("Current exchange rate succesfully downloaded.");
            } else {
                logger.warn("Exchange rate downloading failed.");
            }
        } else {
            if(download_M5_100_Points_Data()) {
                logger.info("Currency data succesfully downloaded.");
                enableCollectingSinglePointData = true;
            } else {
                logger.warn("Currency data downloading failed.");
            }
        }
    }

    private boolean download_M5_100_Points_Data(){
        return dataDownloaderService.saveTimeSeries();
    }

    private boolean downloadCurrentExchangeRate(){
        return dataDownloaderService.saveCurrentExchangeRate();
    }
}
