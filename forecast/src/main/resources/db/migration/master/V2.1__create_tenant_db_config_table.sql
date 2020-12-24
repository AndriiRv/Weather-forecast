CREATE TABLE tenant_db_config
(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_name VARCHAR(30) NOT NULL,
    username VARCHAR (30) NOT NULL,
    password VARCHAR (30) NOT NULL,
    url VARCHAR (100) NOT NULL
);