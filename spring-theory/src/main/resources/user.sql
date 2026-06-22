CREATE DATABASE IF NOT EXISTS springtheory
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_general_ci;

use springtheory;

CREATE TABLE users (
                       id VARCHAR(20) PRIMARY KEY,
                       name VARCHAR(20) NOT NULL,
                       password VARCHAR(20) NOT NULL
);