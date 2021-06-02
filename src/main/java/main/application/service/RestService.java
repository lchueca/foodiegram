package main.application.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class RestService {

    RestTemplate template;
    Gson gson;

    @Value("${positionstack.api.key}")
    private String apiKey;

    public RestService() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        this.template = builder.build();
        this.gson = new Gson();
    }

    public Map<String, Object> getGeoData(Double lat, Double lon) {
        String latitude = lat.toString().replace(",", ".");
        String longitude = lon.toString().replace(",", ".");

        String query = String.format(
                "http://api.positionstack.com/v1/reverse?access_key=%s&query=%s,%s&limit=1",
                apiKey, latitude, longitude);

        List<Map<String, Object>> data = (List<Map<String, Object>>) getJSON(query).get("data");
        return data.get(0);
    }

    private Map<String, Object> getJSON(String url) {

        return gson.fromJson(this.template.getForObject(url, String.class), Map.class);
    }
}