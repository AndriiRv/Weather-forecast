package com.example.weatherforecast.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "ukraineEntityManagerFactory",
        basePackages = {
                "com.example.weatherforecast.tenant.ukraine.city.repository",
                "com.example.weatherforecast.tenant.ukraine.weatherforecast.repository"
        },
        transactionManagerRef = "ukraineTransactionManager"
)
@DependsOn("DBConfig")
public class UkraineDBConfig {

    @Bean("ukraineDatasource")
    public DataSource ukraineDatasource() {
        DBConfigModel ukraineTenant = DBConfig.dbConfigModels.stream()
                .filter(e -> e.getTenantName().equalsIgnoreCase("ukraine_tenant"))
                .findFirst().orElse(new DBConfigModel());

        return DataSourceBuilder.create()
                .url(ukraineTenant.getUrl())
                .username(ukraineTenant.getUsername())
                .password(ukraineTenant.getPassword())
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    @Bean("namedParameterJdbcTemplateWithUkraineDatasource")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplateWithUkraineDatasource(@Qualifier("ukraineDatasource") DataSource dataSource) {
        flywayConfig(dataSource);
        return new NamedParameterJdbcTemplate(dataSource);
    }

    private void flywayConfig(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("db/migration/ukraine_tenant")
                .load();
        flyway.migrate();
    }

    @Bean("ukraineEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean ukraineEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("ukraineDatasource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.example.weatherforecast.tenant.common.model")
                .persistenceUnit("WeatherForecast")
                .build();
    }

    @Bean("ukraineTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("ukraineEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}