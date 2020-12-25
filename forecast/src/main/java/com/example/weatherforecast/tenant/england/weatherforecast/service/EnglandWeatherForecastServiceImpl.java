package com.example.weatherforecast.tenant.england.weatherforecast.service;

import com.example.weatherforecast.tenant.common.model.WeatherForecast;
import com.example.weatherforecast.tenant.england.weatherforecast.repository.EnglandWeatherForecastJDBCRepository;
import com.example.weatherforecast.tenant.england.weatherforecast.repository.EnglandWeatherForecastJPARepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class EnglandWeatherForecastServiceImpl implements EnglandWeatherForecastService {
    private final EnglandWeatherForecastJPARepository englandWeatherForecastJPARepository;
    private final EnglandWeatherForecastJDBCRepository englandWeatherForecastJDBCRepository;

    public EnglandWeatherForecastServiceImpl(EnglandWeatherForecastJPARepository englandWeatherForecastJPARepository,
                                             EnglandWeatherForecastJDBCRepository englandWeatherForecastJDBCRepository) {
        this.englandWeatherForecastJPARepository = englandWeatherForecastJPARepository;
        this.englandWeatherForecastJDBCRepository = englandWeatherForecastJDBCRepository;
    }

    @Override
    public void save(UUID idCity, String temperature) {
        WeatherForecast weatherForecast = new WeatherForecast();
        weatherForecast.setId(UUID.randomUUID());
        weatherForecast.setDate(LocalDate.now());
        weatherForecast.setIdCity(idCity);
        weatherForecast.setTemperature(temperature);

        englandWeatherForecastJPARepository.save(weatherForecast);
    }

    @Override
    public List<WeatherForecast> findAll() {
        return englandWeatherForecastJDBCRepository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        englandWeatherForecastJPARepository.deleteById(id);
    }
}