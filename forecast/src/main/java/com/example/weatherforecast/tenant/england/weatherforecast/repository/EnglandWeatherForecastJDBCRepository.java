package com.example.weatherforecast.tenant.england.weatherforecast.repository;

import com.example.weatherforecast.tenant.common.model.WeatherForecast;

import java.util.List;

public interface EnglandWeatherForecastJDBCRepository {

    List<WeatherForecast> findAll();
}