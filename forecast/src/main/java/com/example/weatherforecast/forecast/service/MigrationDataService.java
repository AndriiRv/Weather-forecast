package com.example.weatherforecast.forecast.service;

import com.example.weatherforecast.tenant.common.model.WeatherForecast;
import com.example.weatherforecast.tenant.common.model.WeatherForecastDto;
import com.example.weatherforecast.tenant.common.service.CommonCityServiceImpl;
import com.example.weatherforecast.tenant.common.service.WeatherForecastService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class MigrationDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonCityServiceImpl.class.getName());

    private final WeatherForecastService ukraineWeatherForecastService;
    private final WeatherForecastService englandWeatherForecastService;

    public MigrationDataService(@Qualifier("ukraineWeatherForecastServiceImpl") WeatherForecastService ukraineWeatherForecastService,
                                @Qualifier("englandWeatherForecastServiceImpl") WeatherForecastService englandWeatherForecastService) {
        this.ukraineWeatherForecastService = ukraineWeatherForecastService;
        this.englandWeatherForecastService = englandWeatherForecastService;
    }

    public void method() {
        List<WeatherForecastDto> weatherInCitiesOfUkraine = ukraineWeatherForecastService.findAll();
        List<WeatherForecastDto> weatherInCitiesOfEngland = englandWeatherForecastService.findAll();
    }

    private void createMigration(String folderMigration, Set<WeatherForecast> set) {
        File migrationFile = createMigrationFile(folderMigration);

        try (FileWriter fileWriter = new FileWriter(migrationFile)) {
            fileWriter.append("INSERT INTO weather_forecast (id, id_city, date, temperature)\n");

            int counter = 0;
            for (WeatherForecast weatherForecast : set) {
                counter++;
                fileWriter
                        .append("VALUES (")
                        .append(String.valueOf(weatherForecast.getId()))
                        .append(", ")
                        .append(String.valueOf(weatherForecast.getIdCity()))
                        .append(", ")
                        .append(String.valueOf(weatherForecast.getDate()))
                        .append(", ")
                        .append(weatherForecast.getTemperature());
                if (counter < set.size()) {
                    fileWriter.append("),\n");
                } else if (counter == set.size()) {
                    fileWriter.append(");");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createMigrationFile(String folderMigration) {
        File file = new File("db/migration/" + folderMigration + "/V" + LocalDateTime.now() + "__migration_of_data.sql");

        if (!file.isFile()) {
            try {
                boolean createdMigrationFile = file.createNewFile();
                if (createdMigrationFile) {
                    LOGGER.info("Created migration to {}", folderMigration);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }
}