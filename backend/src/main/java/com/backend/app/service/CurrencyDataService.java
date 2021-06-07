package backend.app.service;

import com.backend.app.domain.DataPointDto;
import com.backend.app.domain.wrapper.DataPoint;
import com.backend.app.domain.wrapper.TimeSeriesWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class CurrencyDataService {

    private List<DataPointDto> currencyData = new ArrayList<>();

    private Random random = new Random();

    private RestTemplate restTemplate = new RestTemplate();

    private String baseEndPoint = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=TSLA&interval=5min&outputsize=full&apikey=FGW9LY1I2F0A4VIB";
    private String apiKey = "";
    private String apiInstrumentEndPoint = "";

    public CurrencyDataService() {
        for(int i=0; i<2; i++){
            int val = random.nextInt();
            currencyData.add(new DataPointDto(val,(String.valueOf(val))));
        }
    }

    public List<DataPointDto> getData(){
        List<DataPointDto> dataPointDtos = new ArrayList<>();
        Map<String, DataPoint> timeSeries = downloadDataFromApi();
        for (String i : timeSeries.keySet()) {
            double value = Double.parseDouble(timeSeries.get(i).getClose());
            dataPointDtos.add(new DataPointDto(value,i));
        }

        return dataPointDtos;
    }

    public Map<String, DataPoint> downloadDataFromApi(){
        URI url = UriComponentsBuilder.fromHttpUrl(
                baseEndPoint).build().encode().toUri();
        TimeSeriesWrapper wrapper = restTemplate.getForObject(url, TimeSeriesWrapper.class);
        //JSONObject jObj = new JSONObject(data);
        //JSONObject timeseries = jObj.getJSONObject("Time Series (5min)");
        System.out.println(wrapper.getMetaData().toString());
        Map<String, DataPoint> timeSeries = wrapper.getTimeSeries();
        for (String i : timeSeries.keySet()) {
            System.out.println("key: " + i + " value: " + timeSeries.get(i));
        }
        return timeSeries;
    }
}
