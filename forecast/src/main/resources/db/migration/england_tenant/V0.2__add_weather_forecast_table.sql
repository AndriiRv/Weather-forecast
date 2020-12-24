CREATE TABLE weather_forecast
(
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_city     UUID  NOT NULL REFERENCES city (id),
    date        DATE  NOT NULL,
    temperature JSONB NOT NULL
);