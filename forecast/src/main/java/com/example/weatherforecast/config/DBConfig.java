package com.example.weatherforecast.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class DBConfig {
    static List<DBConfigModel> dbConfigModels;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    DBConfig(@Qualifier("namedParameterJdbcTemplateWithMasterDatasource") NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        getDBConfig();
    }

    private void getDBConfig() {
        String sql = "SELECT * FROM tenant_db_config";
        dbConfigModels = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DBConfigModel.class));
    }
}