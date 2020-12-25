package com.example.weatherforecast.tenant.common.model;

import java.time.LocalDate;
import java.util.Objects;

public class WeatherForecastDto {
    private String name;
    private LocalDate date;
    private String temperature;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherForecastDto that = (WeatherForecastDto) o;
        return Objects.equals(name, that.name)
                && Objects.equals(date, that.date)
                && Objects.equals(temperature, that.temperature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, temperature);
    }

    @Override
    public String toString() {
        return "WeatherForecastDto{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", temperature='" + temperature + '\'' +
                '}';
    }
}