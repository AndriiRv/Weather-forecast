package com.example.weatherforecast.tenant.common.service;

import com.example.weatherforecast.tenant.common.model.WeatherForecast;

import java.util.List;
import java.util.UUID;

public interface WeatherForecastService {

    void save(UUID idCity, String temperature);

    List<WeatherForecast> findAll();

    void deleteById(UUID id);
}