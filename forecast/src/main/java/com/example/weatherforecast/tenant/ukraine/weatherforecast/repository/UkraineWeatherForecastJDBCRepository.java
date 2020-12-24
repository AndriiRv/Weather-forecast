package com.example.weatherforecast.tenant.ukraine.weatherforecast.repository;

import com.example.weatherforecast.tenant.common.model.WeatherForecastDto;

import java.util.List;

public interface UkraineWeatherForecastJDBCRepository {

    List<WeatherForecastDto> findAll();
}