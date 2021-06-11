package main.application.service;

import com.google.gson.Gson;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class RestService {

    @Autowired
    RestTemplate template;
    Gson gson;

    @Value("${positionstack.api.key}")
    private String apiKey;

    public RestService() {

        this.gson = new Gson();
    }

    @HystrixCommand(fallbackMethod = "noFunciona")
    public Map<String, Object> getGeoData(Double lat, Double lon) {
        String latitude = lat.toString().replace(",", ".");
        String longitude = lon.toString().replace(",", ".");

        String query = String.format("https://nominatim.openstreetmap.org/reverse?format=json&lat=%s8&lon=%s&accept-language=en", latitude, longitude);

        Map<String, Object> data = (Map<String, Object>) getJSON(query).get("address");

        if (!data.containsKey("city")) {

            if (data.containsKey("village")) {
                data.put("city", data.get("village"));
                data.remove("village");
            }

            if (data.containsKey("town")) {
                data.put("city", data.get("town"));
                data.remove("town");
            }
        }

        if (data.containsKey("road")) {
            data.put("street", data.get("road"));
            data.remove("road");
        }

        return data;
    }

    public Map<String, Object> fallback(Double lat, Double lon) {

        System.out.println("Could not geoCode from neither sources-");
        return null;
    }

    private Map<String, Object> getJSON(String url) {
        return gson.fromJson(this.template.getForObject(url, String.class), Map.class);
    }
}