package com.example.weatherforecast.tenant.england.city.repository;

import com.example.weatherforecast.tenant.common.model.City;

import java.util.List;
import java.util.Optional;

public interface EnglandCityJDBCRepository {

    Optional<City> findByName(String name);

    List<City> findAll();
}