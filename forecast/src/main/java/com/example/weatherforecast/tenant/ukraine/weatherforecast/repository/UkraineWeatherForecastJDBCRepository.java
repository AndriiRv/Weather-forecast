package com.example.weatherforecast.tenant.ukraine.weatherforecast.repository;

import com.example.weatherforecast.tenant.common.model.WeatherForecast;

import java.util.List;

public interface UkraineWeatherForecastJDBCRepository {

    List<WeatherForecast> findAll();
}