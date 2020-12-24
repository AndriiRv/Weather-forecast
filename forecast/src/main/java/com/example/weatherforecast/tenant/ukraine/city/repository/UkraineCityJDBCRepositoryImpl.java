package com.example.weatherforecast.tenant.ukraine.city.repository;

import com.example.weatherforecast.tenant.common.model.City;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UkraineCityJDBCRepositoryImpl implements UkraineCityJDBCRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UkraineCityJDBCRepositoryImpl(@Qualifier("namedParameterJdbcTemplateWithUkraineDatasource") NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<City> findByName(String name) {
        try {
            String sql = "SELECT * FROM city WHERE name = :name";
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    sql,
                    new MapSqlParameterSource("name", name),
                    new BeanPropertyRowMapper<>(City.class))
            );
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<City> findAll() {
        String sql = "SELECT * FROM city";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(City.class));
    }
}