package com.example.weatherforecast.tenant.england.city.service;

import com.example.weatherforecast.tenant.common.model.City;
import com.example.weatherforecast.tenant.england.city.repository.EnglandCityJDBCRepository;
import com.example.weatherforecast.tenant.england.city.repository.EnglandCityJPARepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnglandCityServiceImpl implements EnglandCityService {
    private final EnglandCityJPARepository cityRepository;
    private final EnglandCityJDBCRepository englandCityJDBCRepository;

    public EnglandCityServiceImpl(EnglandCityJPARepository cityRepository,
                                  EnglandCityJDBCRepository englandCityJDBCRepository) {
        this.cityRepository = cityRepository;
        this.englandCityJDBCRepository = englandCityJDBCRepository;
    }

    @Override
    public boolean save(String name) {
        try {
            cityRepository.save(new City(UUID.randomUUID(), name));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Optional<City> findByName(String name) {
        return englandCityJDBCRepository.findByName(name);
    }

    @Override
    public List<City> findAll() {
        return englandCityJDBCRepository.findAll();
    }

    @Override
    public boolean deleteById(UUID id) {
        try {
            cityRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}