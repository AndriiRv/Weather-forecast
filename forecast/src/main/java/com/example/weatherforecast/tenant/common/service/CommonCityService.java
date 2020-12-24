package com.example.weatherforecast.tenant.common.service;

import com.example.weatherforecast.forecast.model.CityDto;

import java.util.List;
import java.util.UUID;

public interface CommonCityService {

    void save(String name, String countryAbbreviation);

    List<CityDto> findAll();

    List<CityDto> findAllByCountry(String countryAbbreviation);

    void deleteById(UUID id, String countryAbbreviation);
}