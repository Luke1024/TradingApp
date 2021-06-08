package com.backend.app.service;

import com.backend.app.domain.DataPointDto;
import com.backend.app.domain.wrapper.DataPointWrapper;
import com.backend.app.domain.wrapper.CurrencyDataWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class InstrumentDataService {

    private List<DataPointDto> currencyData = new ArrayList<>();

    private Random random = new Random();

    private RestTemplate restTemplate = new RestTemplate();

    private String baseEndPoint = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=TSLA&interval=5min&outputsize=full&apikey=FGW9LY1I2F0A4VIB";

    public InstrumentDataService() {
        for(int i=0; i<2; i++){
            int val = random.nextInt();
            currencyData.add(new DataPointDto(val,(String.valueOf(val))));
        }
    }

    public List<DataPointDto> getData(){
        List<DataPointDto> dataPointDtos = new ArrayList<>();
        Map<String, DataPointWrapper> timeSeries = downloadDataFromApi();
        for (String i : timeSeries.keySet()) {
            double value = Double.parseDouble(timeSeries.get(i).getClose());
            dataPointDtos.add(new DataPointDto(value,i));
        }

        return dataPointDtos;
    }

    public Map<String, DataPointWrapper> downloadDataFromApi(){
        URI url = UriComponentsBuilder.fromHttpUrl(
                baseEndPoint).build().encode().toUri();
        CurrencyDataWrapper wrapper = restTemplate.getForObject(url, CurrencyDataWrapper.class);
        //JSONObject jObj = new JSONObject(data);
        //JSONObject timeseries = jObj.getJSONObject("Time Series (5min)");
        System.out.println(wrapper.getMetaDataWrapper().toString());
        Map<String, DataPointWrapper> timeSeries = wrapper.getTimeSeries();
        for (String i : timeSeries.keySet()) {
            System.out.println("key: " + i + " value: " + timeSeries.get(i));
        }
        return timeSeries;
    }
}
