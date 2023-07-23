CREATE TABLE IF NOT EXISTS cliente (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `nombre` VARCHAR(255) NOT NULL,
    `genero` VARCHAR(255) NOT NULL,
    `edad` INT NOT NULL,
    `identificacion` VARCHAR(255) UNIQUE NOT NULL,
    `direccion` VARCHAR(255) NOT NULL,
    `telefono` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `estado` BOOLEAN NOT NULL
);