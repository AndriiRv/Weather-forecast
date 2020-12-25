package com.example.weatherforecast.forecast.service;

import com.example.weatherforecast.tenant.common.model.WeatherForecast;
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

@Service
public class MigrationDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonCityServiceImpl.class.getName());

    private static final String NAME_OF_FOLDER_MIGRATION = "migrations/";
    private static final String INFO_ABOUT_CREATED_FOLDER = "Created {} folder";
    private static final String INFO_ABOUT_ALREADY_CREATED_FOLDER = "Folder {} already created";
    private static final String INFO_ABOUT_CREATED_MIGRATION = "Created migration to {} - {}";
    private static final String PATH_ROOT_PROJECT = "user.dir";

    private final WeatherForecastService ukraineWeatherForecastService;
    private final WeatherForecastService englandWeatherForecastService;

    public MigrationDataService(@Qualifier("ukraineWeatherForecastServiceImpl") WeatherForecastService ukraineWeatherForecastService,
                                @Qualifier("englandWeatherForecastServiceImpl") WeatherForecastService englandWeatherForecastService) {
        this.ukraineWeatherForecastService = ukraineWeatherForecastService;
        this.englandWeatherForecastService = englandWeatherForecastService;
    }

    public void initWeatherForecastMigration() {
        List<WeatherForecast> weatherInCitiesOfUkraine = ukraineWeatherForecastService.findAll();
        createMigrationFilesWithData("ukraine_tenant", weatherInCitiesOfUkraine);

        List<WeatherForecast> weatherInCitiesOfEngland = englandWeatherForecastService.findAll();
        createMigrationFilesWithData("england_tenant", weatherInCitiesOfEngland);
    }

    private void createMigrationFilesWithData(String folderMigration, List<WeatherForecast> weatherForecasts) {
        String pathToCommonFolderMigration = NAME_OF_FOLDER_MIGRATION + folderMigration;
        createMigrationFolders(pathToCommonFolderMigration);

        String localDateTimeStr = UtilService.getLocalDateTimeWithFormatter(LocalDateTime.now());
        File migrationFile = createMigrationFile(pathToCommonFolderMigration, localDateTimeStr);

        try (FileWriter fileWriter = new FileWriter(migrationFile)) {
            fileWriter
                    .append("INSERT INTO weather_forecast (id, id_city, date, temperature)\n")
                    .append("VALUES ");

            int counter = 0;
            for (WeatherForecast weatherForecast : weatherForecasts) {
                counter++;
                fileWriter.append("(\'").append(String.valueOf(weatherForecast.getId())).append("\'")
                        .append(", ").append("\'").append(String.valueOf(weatherForecast.getIdCity())).append("\'")
                        .append(", ")
                        .append(String.valueOf(weatherForecast.getDate()))
                        .append(", ").append("\'").append(weatherForecast.getTemperature()).append("\'");
                if (counter < weatherForecasts.size()) {
                    fileWriter.append("),\n");
                } else if (counter == weatherForecasts.size()) {
                    fileWriter.append(");");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createMigrationFolders(String pathToCommonFolderMigration) {
        createFolderWithLog(NAME_OF_FOLDER_MIGRATION);
        createFolderWithLog(pathToCommonFolderMigration);
    }

    private void createFolderWithLog(String folderMigration) {
        boolean isFolderMigrationCreated = createFolder(folderMigration);
        if (isFolderMigrationCreated) {
            LOGGER.info(INFO_ABOUT_CREATED_FOLDER, folderMigration);
        } else {
            LOGGER.info(INFO_ABOUT_ALREADY_CREATED_FOLDER, NAME_OF_FOLDER_MIGRATION);
        }
    }

    private boolean createFolder(String name) {
        File folder = new File(System.getProperty(PATH_ROOT_PROJECT) + "\\" + name);
        if (!folder.exists()) {
            return folder.mkdir();
        }
        return false;
    }

    private File createMigrationFile(String folderMigration, String localDateTimeStr) {
        File file = new File(System.getProperty(PATH_ROOT_PROJECT) + "\\" + folderMigration + "/V" + localDateTimeStr + "__migration_of_data.sql");
        try {
            boolean createdMigrationFile = file.createNewFile();
            if (createdMigrationFile) {
                LOGGER.info(INFO_ABOUT_CREATED_MIGRATION, folderMigration, localDateTimeStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}