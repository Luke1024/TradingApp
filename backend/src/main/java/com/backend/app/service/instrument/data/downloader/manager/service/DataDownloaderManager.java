package com.backend.app.service.instrument.data.downloader.manager.service;

import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.InstrumentDataDownloaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataDownloaderManager {

    @Autowired
    private InstrumentDataDownloaderService dataDownloaderService;

    private boolean enableCollectingSinglePointData;

    public DataDownloaderManager() {
        this.enableCollectingSinglePointData = false;
    }

    @Scheduled(fixedRate = 300000)
    private void downloadStartingData(){
        if(enableCollectingSinglePointData) {
            download_M5_single_point_data();
        } else {
            download_M5_100_Points_Data();
            enableCollectingSinglePointData = true;
        }

    }

    private void download_M5_100_Points_Data(){
        //dataDownloaderService.loadLast100DataPointsToDatabase();
    }

    private void download_M5_single_point_data(){
        //dataDownloaderService.getCurrentExchangeRate();
    }
}
