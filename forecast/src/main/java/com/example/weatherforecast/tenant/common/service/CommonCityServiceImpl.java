package com.example.weatherforecast.tenant.common.service;

import com.example.weatherforecast.forecast.model.CityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.weatherforecast.forecast.service.UtilService.ENGLAND_ABBREVIATION;
import static com.example.weatherforecast.forecast.service.UtilService.UKRAINE_ABBREVIATION;

@Service
public class CommonCityServiceImpl implements CommonCityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonCityServiceImpl.class.getName());

    private final CityService ukraineCityService;
    private final CityService englandCityService;
    private final CityConverter converter;

    public CommonCityServiceImpl(@Qualifier("ukraineCityServiceImpl") CityService ukraineCityService,
                                 @Qualifier("englandCityServiceImpl") CityService englandCityService) {
        this.ukraineCityService = ukraineCityService;
        this.englandCityService = englandCityService;
        this.converter = new CityConverter();
    }

    @Override
    public void save(String name, String countryAbbreviation) {
        String lowerCaseName = name.toLowerCase();

        boolean isSaved = false;
        if (countryAbbreviation.equalsIgnoreCase(UKRAINE_ABBREVIATION)) {
            isSaved = ukraineCityService.save(lowerCaseName);
        } else if (countryAbbreviation.equalsIgnoreCase(ENGLAND_ABBREVIATION)) {
            isSaved = englandCityService.save(lowerCaseName);
        }

        if (isSaved) {
            LOGGER.info("City [{}, {}] saved successfully", lowerCaseName, countryAbbreviation.toUpperCase());
        }
    }

    @Override
    public List<CityDto> findAll() {
        List<CityDto> citiesOfUkraine = findAllInDB(ukraineCityService, UKRAINE_ABBREVIATION);
        List<CityDto> citiesOfEngland = findAllInDB(englandCityService, ENGLAND_ABBREVIATION);

        List<CityDto> result = Stream.concat(citiesOfUkraine.stream(), citiesOfEngland.stream()).collect(Collectors.toList());

        showSelectedCitiesWithNamesAndCountries(result);
        return result;
    }

    @Override
    public List<CityDto> findAllByCountry(String countryAbbreviation) {
        List<CityDto> result = new ArrayList<>();
        if (countryAbbreviation.equalsIgnoreCase(UKRAINE_ABBREVIATION)) {
            result = findAllInDB(ukraineCityService, UKRAINE_ABBREVIATION);
        } else if (countryAbbreviation.equalsIgnoreCase(ENGLAND_ABBREVIATION)) {
            result = findAllInDB(englandCityService, ENGLAND_ABBREVIATION);
        }

        showSelectedCitiesWithNamesAndCountries(result);
        return result;
    }

    private List<CityDto> findAllInDB(CityService cityService, String abbreviation) {
        return cityService.findAll().stream()
                .map(e -> converter.convertToCityDto(e, abbreviation))
                .collect(Collectors.toList());
    }

    private void showSelectedCitiesWithNamesAndCountries(List<CityDto> result) {
        List<String> citiesWithNamesAndCountries = result.stream()
                .map(e -> "(" + e.getName() + ", " + e.getCountry() + ")")
                .collect(Collectors.toList());
        LOGGER.info("Fetched of the cities: {}", citiesWithNamesAndCountries);
    }

    @Override
    public void deleteById(UUID id, String countryAbbreviation) {
        boolean isDeleted = false;
        if (countryAbbreviation.equalsIgnoreCase(UKRAINE_ABBREVIATION)) {
            isDeleted = ukraineCityService.deleteById(id);
        } else if (countryAbbreviation.equalsIgnoreCase(ENGLAND_ABBREVIATION)) {
            isDeleted = englandCityService.deleteById(id);
        }

        if (isDeleted) {
            LOGGER.info("City [{}, {}] deleted successfully", id, countryAbbreviation.toUpperCase());
        }
    }
}