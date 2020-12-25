package com.example.weatherforecast.forecast.service;

import com.example.weatherforecast.forecast.model.CityDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class UtilService {
    public static final String UKRAINE_ABBREVIATION = "UA";
    static final String UKRAINE_TENANT_ID = "1";
    public static final String ENGLAND_ABBREVIATION = "GB";
    static final String ENGLAND_TENANT_ID = "2";
    private static final String DATE_FORMAT = "yyyyMMddHHmmss";
    private static final String NAME_OF_CITIES_JSON = "cities.json";

    private static final Set<CityDto> CITIES = findAllCity();

    private static Set<CityDto> findAllCity() {
        try {
            File file = new File(Objects.requireNonNull(UtilService.class.getClassLoader().getResource(NAME_OF_CITIES_JSON)).getFile());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(file, new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptySet();
    }

    public static int getCityAPIId(String nameOfCity, String abbreviationOfCountry) {
        return CITIES.stream()
                .filter(e -> e.getName().equalsIgnoreCase(nameOfCity))
                .filter(e -> e.getCountry().equalsIgnoreCase(abbreviationOfCountry))
                .map(CityDto::getId)
                .findFirst().orElse(0);
    }

    static CityDto getCityByAPIId(int id) {
        return CITIES.stream()
                .filter(e -> e.getId() == id)
                .findFirst().orElse(null);
    }

    static String getLocalDateTimeWithFormatter(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return localDateTime.format(formatter);
    }
}