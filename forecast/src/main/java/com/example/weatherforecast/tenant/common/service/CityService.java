package com.example.weatherforecast.tenant.common.service;

import com.example.weatherforecast.tenant.common.model.City;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CityService {

    boolean save(String name);

    Optional<City> findByName(String name);

    List<City> findAll();

    boolean deleteById(UUID id);
}