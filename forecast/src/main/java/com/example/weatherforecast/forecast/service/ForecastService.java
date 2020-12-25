package com.example.weatherforecast.forecast.service;

import com.example.weatherforecast.forecast.model.CityDto;
import com.example.weatherforecast.tenant.common.model.City;
import com.example.weatherforecast.tenant.common.service.CityService;
import com.example.weatherforecast.tenant.common.service.CommonCityServiceImpl;
import com.example.weatherforecast.tenant.common.service.WeatherForecastService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.UUID;

import static com.example.weatherforecast.forecast.service.UtilService.ENGLAND_TENANT_ID;
import static com.example.weatherforecast.forecast.service.UtilService.UKRAINE_TENANT_ID;

@Service
public class ForecastService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonCityServiceImpl.class.getName());

    private final String appId;
    private final WeatherForecastService englandWeatherForecastService;
    private final WeatherForecastService ukraineWeatherForecastService;
    private final CityService englandCityService;
    private final CityService ukraineCityService;

    private final RestTemplate restTemplate;

    public ForecastService(@Value("${weather-forecast.appid}") String appId,
                           @Qualifier("englandWeatherForecastServiceImpl") WeatherForecastService englandWeatherForecastService,
                           @Qualifier("ukraineWeatherForecastServiceImpl") WeatherForecastService ukraineWeatherForecastService,
                           @Qualifier("englandCityServiceImpl") CityService englandCityService,
                           @Qualifier("ukraineCityServiceImpl") CityService ukraineCityService) {
        this.appId = appId;
        this.englandWeatherForecastService = englandWeatherForecastService;
        this.ukraineWeatherForecastService = ukraineWeatherForecastService;
        this.englandCityService = englandCityService;
        this.ukraineCityService = ukraineCityService;

        this.restTemplate = new RestTemplate();
    }

    public void getCurrentForecastBy(int id, String tenantId) {
        ResponseEntity<String> request = restTemplate.exchange(
                "http://api.openweathermap.org/data/2.5/weather?id=" + id + "&units=metric&appid=" + appId,
                HttpMethod.GET,
                null,
                String.class
        );

        ObjectNode node;
        try {
            node = new ObjectMapper().readValue(Objects.requireNonNull(request.getBody()), ObjectNode.class);
            String temperature = Objects.requireNonNull(node).get("main").toString();
            saveToTenant(id, tenantId, temperature);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getForecastForCertainPeriodBy(int id, String tenantId, String startDate, String endDate) {
        //TODO find free weather forecast API with fetch forecast for certain period
    }

    private void saveToTenant(int cityId, String tenantId, String temperature) {
        CityDto cityDto = UtilService.getCityByAPIId(cityId);
        String nameOfCity = cityDto.getName();
        String country = cityDto.getCountry();

        LOGGER.info("Get temperature to [{}, {}]", nameOfCity, country);

        if (tenantId.equals(UKRAINE_TENANT_ID)) {
            saveToDb(cityDto, ukraineCityService, ukraineWeatherForecastService, temperature);
        } else if (tenantId.equals(ENGLAND_TENANT_ID)) {
            saveToDb(cityDto, englandCityService, englandWeatherForecastService, temperature);
        }
    }

    private void saveToDb(CityDto cityDto, CityService cityService, WeatherForecastService weatherForecastService, String temperature) {
        UUID cityId = cityService.findByName(cityDto.getName().toLowerCase()).orElse(new City()).getId();
        weatherForecastService.save(cityId, temperature);

        LOGGER.info("Save temperature to [{}, {}]", cityDto.getName(), cityDto.getCountry());
    }
}