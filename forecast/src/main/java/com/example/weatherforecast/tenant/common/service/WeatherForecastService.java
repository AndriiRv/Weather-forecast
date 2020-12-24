package com.example.weatherforecast.tenant.common.service;

import com.example.weatherforecast.tenant.common.model.WeatherForecastDto;

import java.util.List;
import java.util.UUID;

public interface WeatherForecastService {

    void save(UUID idCity, String temperature);

    List<WeatherForecastDto> findAll();

    void deleteById(UUID id);
}