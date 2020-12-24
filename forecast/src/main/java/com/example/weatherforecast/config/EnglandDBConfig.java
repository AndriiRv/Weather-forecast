package com.example.weatherforecast.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "englandEntityManagerFactory",
        basePackages = {
                "com.example.weatherforecast.tenant.england.city.repository",
                "com.example.weatherforecast.tenant.england.weatherforecast.repository"
        },
        transactionManagerRef = "englandTransactionManager"
)
@DependsOn("DBConfig")
public class EnglandDBConfig {

    @Bean("englandDatasource")
    public DataSource englandDatasource() {
        DBConfigModel englandTenant = DBConfig.dbConfigModels.stream()
                .filter(e -> e.getTenantName().equalsIgnoreCase("england_tenant"))
                .findFirst().orElse(new DBConfigModel());

        return DataSourceBuilder.create()
                .url(englandTenant.getUrl())
                .username(englandTenant.getUsername())
                .password(englandTenant.getPassword())
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    @Bean("namedParameterJdbcTemplateWithEnglandDatasource")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplateWithEnglandDatasource(@Qualifier("englandDatasource") DataSource dataSource) {
        flywayConfig(dataSource);
        return new NamedParameterJdbcTemplate(dataSource);
    }

    private void flywayConfig(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("db/migration/england_tenant")
                .load();
        flyway.migrate();
    }

    @Bean("englandEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean englandEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("englandDatasource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.example.weatherforecast.tenant.common.model")
                .persistenceUnit("WeatherForecast")
                .build();
    }

    @Bean("englandTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("englandEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}