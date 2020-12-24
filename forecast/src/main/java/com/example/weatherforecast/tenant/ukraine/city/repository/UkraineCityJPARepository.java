package com.example.weatherforecast.tenant.ukraine.city.repository;

import com.example.weatherforecast.tenant.common.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UkraineCityJPARepository extends CrudRepository<City, UUID> {

}