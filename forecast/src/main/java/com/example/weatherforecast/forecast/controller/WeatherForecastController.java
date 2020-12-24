package com.example.weatherforecast.forecast.controller;

import com.example.weatherforecast.forecast.service.ForecastService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/weather-forecast")
public class WeatherForecastController {
    private final ForecastService forecastService;

    public WeatherForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping("/city/{id}")
    public void getForecastByCityId(@PathVariable int id, @RequestHeader("tenantId") String tenantId) {
        if (!Objects.isNull(tenantId)) {
            forecastService.getForecastBy(id, tenantId);
        }
    }

    @GetMapping("/backup")
    public void getBackupDatabases() {

    }
}