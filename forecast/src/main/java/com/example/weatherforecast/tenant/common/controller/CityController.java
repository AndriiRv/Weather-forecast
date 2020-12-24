package com.example.weatherforecast.tenant.common.controller;

import com.example.weatherforecast.forecast.model.CityDto;
import com.example.weatherforecast.tenant.common.service.CommonCityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/weather-forecast")
public class CityController {
    private CommonCityService cityService;

    public CityController(CommonCityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/city/all")
    public List<CityDto> findAll() {
        return cityService.findAll();
    }

    @GetMapping("/city/all-by-country")
    public List<CityDto> findAll(@RequestParam String countryAbbreviation) {
        return cityService.findAllByCountry(countryAbbreviation);
    }

    @PostMapping("/city")
    public void save(@RequestParam String name, @RequestParam String countryAbbreviation) {
        cityService.save(name, countryAbbreviation);
    }

    @DeleteMapping("/city")
    public void deleteById(@RequestParam UUID id, @RequestParam String countryAbbreviation) {
        cityService.deleteById(id, countryAbbreviation);
    }
}