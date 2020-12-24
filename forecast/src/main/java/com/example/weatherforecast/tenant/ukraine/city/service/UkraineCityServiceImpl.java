package com.example.weatherforecast.tenant.ukraine.city.service;

import com.example.weatherforecast.tenant.common.model.City;
import com.example.weatherforecast.tenant.ukraine.city.repository.UkraineCityJDBCRepository;
import com.example.weatherforecast.tenant.ukraine.city.repository.UkraineCityJPARepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UkraineCityServiceImpl implements UkraineCityService {
    private final UkraineCityJPARepository ukraineCityJPARepository;
    private final UkraineCityJDBCRepository ukraineCityJDBCRepository;

    public UkraineCityServiceImpl(UkraineCityJPARepository ukraineCityJPARepository,
                                  UkraineCityJDBCRepository ukraineJdbcRepository) {
        this.ukraineCityJPARepository = ukraineCityJPARepository;
        this.ukraineCityJDBCRepository = ukraineJdbcRepository;
    }

    @Override
    public boolean save(String name) {
        try {
            ukraineCityJPARepository.save(new City(UUID.randomUUID(), name));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Optional<City> findByName(String name) {
        return ukraineCityJDBCRepository.findByName(name);
    }

    @Override
    public List<City> findAll() {
        return ukraineCityJDBCRepository.findAll();
    }

    @Override
    public boolean deleteById(UUID id) {
        try {
            ukraineCityJPARepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}