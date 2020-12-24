package com.example.weatherforecast.tenant.common.service;

import com.example.weatherforecast.forecast.model.CityDto;
import com.example.weatherforecast.forecast.service.UtilService;
import com.example.weatherforecast.tenant.common.model.City;

class CityConverter {

    CityDto convertToCityDto(City city, String countryAbbreviation) {
        CityDto cityDto = new CityDto();
        cityDto.setId(UtilService.getCityAPIId(city.getName(), countryAbbreviation));
        cityDto.setName(city.getName());
        cityDto.setCountry(countryAbbreviation);
        return cityDto;
    }
}