package main.application.service;

import com.google.gson.Gson;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RestService {

    RestTemplate template;
    Gson gson;

    public RestService() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        this.template = builder.build();
        this.gson = new Gson();
    }

    public Map<String, Object> getJSON(String url) {

        return gson.fromJson(this.template.getForObject(url, String.class), Map.class);
    }
}