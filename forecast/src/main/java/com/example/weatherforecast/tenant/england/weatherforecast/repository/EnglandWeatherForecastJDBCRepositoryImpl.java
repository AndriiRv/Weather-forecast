package com.example.weatherforecast.tenant.england.weatherforecast.repository;

import com.example.weatherforecast.tenant.common.model.WeatherForecast;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnglandWeatherForecastJDBCRepositoryImpl implements EnglandWeatherForecastJDBCRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EnglandWeatherForecastJDBCRepositoryImpl(
            @Qualifier("namedParameterJdbcTemplateWithEnglandDatasource") NamedParameterJdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<WeatherForecast> findAll() {
        String sql = "" +
                "SELECT * " +
                "FROM weather_forecast AS wf " +
                "INNER JOIN city AS C ON c.id = wf.id_city";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WeatherForecast.class));
    }
}