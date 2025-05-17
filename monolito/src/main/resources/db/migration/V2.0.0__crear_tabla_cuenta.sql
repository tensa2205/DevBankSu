CREATE TABLE IF NOT EXISTS cuenta (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cliente_id BIGINT NOT NULL,
    nro_cuenta BIGINT UNIQUE NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    saldo_inicial DECIMAL(19, 2) NOT NULL,
    saldo_actual DECIMAL(19, 2) NOT NULL,
    estado BOOLEAN NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);