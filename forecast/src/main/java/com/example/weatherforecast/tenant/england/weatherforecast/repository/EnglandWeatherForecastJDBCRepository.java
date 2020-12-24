package com.example.weatherforecast.tenant.england.weatherforecast.repository;

import com.example.weatherforecast.tenant.common.model.WeatherForecastDto;

import java.util.List;

public interface EnglandWeatherForecastJDBCRepository {

    List<WeatherForecastDto> findAll();
}