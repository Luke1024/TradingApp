package com.backend.app.service.instrument.data.downloader.manager.service.downloader.service;

import com.backend.app.service.DataPointAndExchangeRateService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InstrumentDataDownloaderServiceTest {

    @Autowired
    private InstrumentDataDownloaderService dataDownloaderService;

    @Autowired
    private DataPointAndExchangeRateService dataPointAndExchangeRateService;
/*
    @Test
    public void print100DataPointsDownloadedData(){
        dataDownloaderService.loadLast100DataPointsToDatabase();
        List<DataPoint> dataPoints = dataPointService.getDataPoints();
        Assert.assertEquals(1,1);
        for(DataPoint dataPoint: dataPoints){
            System.out.println(dataPoint.toString());
        }
    }
    */
}