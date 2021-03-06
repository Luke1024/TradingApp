package com.backend.app.service;

import com.backend.app.domain.DataPoint;
import com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.utilities.DataPointAndExchangeRateService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataPointAndExchangeRateServiceTest {

    private Random random = new Random();

    @Autowired
    private DataPointAndExchangeRateService dataPointAndExchangeRateService;

    @After
    public void cleanRepo(){
        dataPointAndExchangeRateService.getAllDataPoints().clear();
    }

    @Test
    public void testSavingAndRetrieving100DataPoints(){

        List<DataPoint> dataPointListToSave = new ArrayList<>();
        for(int i=0; i<100; i++){
            dataPointListToSave.add(randomPointGenerator(generateDateBasedOnInteger(i)));
        }

        dataPointAndExchangeRateService.saveDataPoints(dataPointListToSave);
        Assert.assertEquals(dataPointListToSave.toString(), dataPointAndExchangeRateService.get100DataPoints().toString());
    }

    @Test
    public void testSavingAndRetrievingDataPointsInReverseDataOrder(){

        List<DataPoint> dataPointListToSave = new ArrayList<>();
        for(int i=0; i<100; i++){
            dataPointListToSave.add(randomPointGenerator(generateDateBasedOnInteger(99-i)));
        }

        dataPointAndExchangeRateService.saveDataPoints(dataPointListToSave);

        //Expected value is 1 because if array is empty service block saving
        // older points when first saved point working as reference.
        Assert.assertEquals(1, dataPointAndExchangeRateService.get100DataPoints().size());
    }

    @Test
    public void testSavingAndRetrievingDataPointsWithTheSameDate(){

        List<DataPoint> dataPointListToSave = new ArrayList<>();
        for(int i=0; i<100; i++){
            dataPointListToSave.add(randomPointGenerator(generateDateBasedOnInteger(1)));
        }

        dataPointAndExchangeRateService.saveDataPoints(dataPointListToSave);

        //Expected value is 1 because if array is empty service block saving
        // older points when first saved point working as reference.
        Assert.assertEquals(1, dataPointAndExchangeRateService.get100DataPoints().size());
    }

    private LocalDateTime generateDateBasedOnInteger(int i){
        return LocalDateTime.now().minusMinutes(i);
    }

    private DataPoint randomPointGenerator(LocalDateTime timeStamp){
        double randomValue = random.nextDouble();
        return new DataPoint(timeStamp, randomValue);
    }
}