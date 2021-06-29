package com.backend.app.service.instrument.data.downloader.manager.service.downloader.service.utilities;

import com.backend.app.domain.DataPoint;
import com.backend.app.domain.DataPointsPackage;
import com.backend.app.domain.MetaData;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataPointsRetrieverServiceTest {

    @Autowired
    private DataPointsRetrieverService retrieverService;

    @Test
    @Ignore
    public void printDataDownloadedFromApi(){
        Optional<DataPointsPackage> dataPointsPackageOptional = retrieverService.getTimeSeriesData();
        DataPointsPackage dataPointsPackage = dataPointsPackageOptional.get();

        MetaData metaData = dataPointsPackage.getMetaData().get();
        System.out.println("Printing metadata: ");
        System.out.println(metaData.toString());

        List<DataPoint> dataPointList = dataPointsPackage.getDataPoints();

        for(DataPoint dataPoint : dataPointList){
            System.out.println(dataPoint.toString());
        }
    }
}