package com.example.weatherforecast.tenant.england.city.repository;

import com.example.weatherforecast.tenant.common.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnglandCityJPARepository extends CrudRepository<City, UUID> {

}