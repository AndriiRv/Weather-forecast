package com.example.scheduler.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class WeatherForecastSchedulerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherForecastSchedulerService.class.getName());

    private static final String UKRAINE_ABBREVIATION = "UA";
    private static final String UKRAINE_TENANT_ID = "1";
    private static final String ENGLAND_ABBREVIATION = "GB";
    private static final String ENGLAND_TENANT_ID = "2";
    private static final String GET_ALL_CITIES_URL = "http://localhost:8080/weather-forecast/city/all";
    private static final String GET_WEATHER_BY_CITY_ID_URL = "http://localhost:8080/weather-forecast/city/";
    private static final String GET_BACKUP_URL = "http://localhost:8080/weather-forecast/backup";
    private static final String INFO_ABOUT_EXECUTE_REQUEST = "Execute request to {}";

    private final RestTemplate restTemplate;

    public WeatherForecastSchedulerService() {
        this.restTemplate = new RestTemplate();
    }

    @Scheduled(fixedRate = 86400000)
    private void getForecast() throws JsonProcessingException {
        ResponseEntity<String> request = getCities();

        if (!Objects.isNull(request.getBody()) && !request.getBody().isBlank()) {
            JsonNode jsonNode = new ObjectMapper().readTree(request.getBody());

            for (JsonNode node : jsonNode) {
                getForecastByCityId(node);
            }
        }
    }

    private ResponseEntity<String> getCities() {
        ResponseEntity<String> request = restTemplate.exchange(GET_ALL_CITIES_URL, HttpMethod.GET, null, String.class);
        LOGGER.info(INFO_ABOUT_EXECUTE_REQUEST, GET_ALL_CITIES_URL);
        return request;
    }

    private void getForecastByCityId(JsonNode node) {
        String cityId = node.get("id").toString();
        HttpHeaders headers = getHeaders(node);
        restTemplate.exchange(GET_WEATHER_BY_CITY_ID_URL + cityId, HttpMethod.GET, new HttpEntity<>(headers), ResponseEntity.class);
        LOGGER.info(INFO_ABOUT_EXECUTE_REQUEST, GET_WEATHER_BY_CITY_ID_URL + cityId);
    }

    private HttpHeaders getHeaders(JsonNode node) {
        HttpHeaders headers = new HttpHeaders();
        String country = node.get("country").toString().replace("\"", "");
        if (country.equalsIgnoreCase(UKRAINE_ABBREVIATION)) {
            headers.set("tenantId", UKRAINE_TENANT_ID);
        } else if (country.equalsIgnoreCase(ENGLAND_ABBREVIATION)) {
            headers.set("tenantId", ENGLAND_TENANT_ID);
        }
        return headers;
    }

    @Scheduled(fixedRate = 604800000)
    private void getBackup() {
        restTemplate.exchange(GET_BACKUP_URL, HttpMethod.GET, null, Object.class);
        LOGGER.info(INFO_ABOUT_EXECUTE_REQUEST, GET_BACKUP_URL);
    }
}