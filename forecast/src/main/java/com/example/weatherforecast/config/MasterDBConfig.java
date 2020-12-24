package com.example.weatherforecast.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class MasterDBConfig {
    private Environment environment;

    public MasterDBConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean("masterDatasource")
    @Primary
    public DataSource englandDatasource() {
        return DataSourceBuilder.create()
                .url(environment.getProperty("spring.datasource.url"))
                .username(environment.getProperty("spring.datasource.username"))
                .password(environment.getProperty("spring.datasource.password"))
                .driverClassName(environment.getProperty("spring.datasource.driver-class-name"))
                .build();
    }

    @Bean("namedParameterJdbcTemplateWithMasterDatasource")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplateWithUkraineDatasource(@Qualifier("masterDatasource") DataSource dataSource) {
        flywayConfig(dataSource);
        return new NamedParameterJdbcTemplate(dataSource);
    }

    private void flywayConfig(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("db/migration/master")
                .load();
        flyway.migrate();
    }
}