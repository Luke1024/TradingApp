package com.backend.app.service.instrument.data.downloader.manager.service;

import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.InstrumentDataDownloaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataDownloaderManager {

    @Autowired
    private InstrumentDataDownloaderService dataDownloaderService;

    private boolean enableCollectingData;

    public DataDownloaderManager() {
        this.enableCollectingData = false;
        downloadStartingData();
        this.enableCollectingData = true;
    }

    private void downloadStartingData(){
        downloadM5100PointsData();
    }

    private void downloadM5100PointsData(){
        dataDownloaderService.loadLast100DataPointsToDatabase();
    }

    @Scheduled(fixedRate = 300000)
    public void runDataCollection(){
    }
}
