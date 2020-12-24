package com.example.scheduler.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class WeatherForecastSchedulerService {
    private static final String UKRAINE_ABBREVIATION = "UA";
    private static final String ENGLAND_ABBREVIATION = "GB";

    //    @Scheduled(fixedRate = 5000)
    //    @Scheduled(cron = "*/86400 * * * * *") //one time in 24 hours
    @Scheduled(cron = "*/5 * * * * *") //one time in 5 sec
    public void scheduledWeatherForecast() {
        getForecast();
    }

    private void getForecast() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Object> exchange = restTemplate.exchange("http://localhost:8080/weather-forecast/city/all", HttpMethod.GET, null, Object.class);
        List<Map<String, String>> body = (List<Map<String, String>>) exchange.getBody();

        HttpHeaders headers = new HttpHeaders();
        for (Map<String, String> element : body) {
            if (element.get("country").equalsIgnoreCase(UKRAINE_ABBREVIATION)) {
                headers.set("tenantId", String.valueOf(1));
            } else if (element.get("country").equalsIgnoreCase(ENGLAND_ABBREVIATION)) {
                headers.set("tenantId", String.valueOf(2));
            }

            String cityId = String.valueOf(element.get("id"));
            restTemplate.exchange(
                    "http://localhost:8080/weather-forecast/city/" + cityId,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    ResponseEntity.class
            );
        }
    }
}