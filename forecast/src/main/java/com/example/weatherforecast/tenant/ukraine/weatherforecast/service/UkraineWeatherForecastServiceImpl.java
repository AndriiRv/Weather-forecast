package com.example.weatherforecast.tenant.ukraine.weatherforecast.service;

import com.example.weatherforecast.tenant.common.model.WeatherForecast;
import com.example.weatherforecast.tenant.common.model.WeatherForecastDto;
import com.example.weatherforecast.tenant.ukraine.weatherforecast.repository.UkraineWeatherForecastJDBCRepository;
import com.example.weatherforecast.tenant.ukraine.weatherforecast.repository.UkraineWeatherForecastJPARepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class UkraineWeatherForecastServiceImpl implements UkraineWeatherForecastService {
    private final UkraineWeatherForecastJPARepository ukraineWeatherForecastJPARepository;
    private final UkraineWeatherForecastJDBCRepository ukraineWeatherForecastJDBCRepository;

    public UkraineWeatherForecastServiceImpl(UkraineWeatherForecastJPARepository ukraineWeatherForecastJPARepository,
                                             UkraineWeatherForecastJDBCRepository ukraineWeatherForecastJDBCRepository) {
        this.ukraineWeatherForecastJPARepository = ukraineWeatherForecastJPARepository;
        this.ukraineWeatherForecastJDBCRepository = ukraineWeatherForecastJDBCRepository;
    }

    @Override
    public void save(UUID idCity, String temperature) {
        WeatherForecast weatherForecast = new WeatherForecast();
        weatherForecast.setId(UUID.randomUUID());
        weatherForecast.setDate(LocalDate.now());
        weatherForecast.setIdCity(idCity);
        weatherForecast.setTemperature(temperature);

        ukraineWeatherForecastJPARepository.save(weatherForecast);
    }

    @Override
    public List<WeatherForecastDto> findAll() {
        return ukraineWeatherForecastJDBCRepository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        ukraineWeatherForecastJPARepository.deleteById(id);
    }
}