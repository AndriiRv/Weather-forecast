package com.example.weatherforecast.forecast.controller;

import com.example.weatherforecast.forecast.service.ForecastService;
import com.example.weatherforecast.forecast.service.MigrationDataService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/weather-forecast")
public class WeatherForecastController {
    private final ForecastService forecastService;
    private final MigrationDataService migrationDataService;

    public WeatherForecastController(ForecastService forecastService,
                                     MigrationDataService migrationDataService) {
        this.forecastService = forecastService;
        this.migrationDataService = migrationDataService;
    }

    @GetMapping("/city/{id}")
    public void getForecastByCityId(@PathVariable int id, @RequestHeader("tenantId") String tenantId) {
        if (!Objects.isNull(tenantId)) {
            forecastService.getCurrentForecastBy(id, tenantId);
        }
    }

    @GetMapping("/city/{id}/weather")
    public void getForecastByCityId(
            @PathVariable int id,
            @RequestHeader("tenantId") String tenantId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        if (!Objects.isNull(tenantId)) {
            forecastService.getForecastForCertainPeriodBy(id, tenantId, startDate, endDate);
        }
    }

    @GetMapping("/backup")
    public void getBackupDatabases() {
        migrationDataService.initWeatherForecastMigration();
    }
}