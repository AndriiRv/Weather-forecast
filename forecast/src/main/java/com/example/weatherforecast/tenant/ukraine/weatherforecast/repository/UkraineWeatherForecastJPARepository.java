package com.example.weatherforecast.tenant.ukraine.weatherforecast.repository;

import com.example.weatherforecast.tenant.common.model.WeatherForecast;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UkraineWeatherForecastJPARepository extends CrudRepository<WeatherForecast, UUID> {

}