CREATE TABLE IF NOT EXISTS movimiento (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cuenta_id BIGINT NOT NULL,
    fecha DATE NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    valor DECIMAL(19, 2) NOT NULL,
    saldo_disponible DECIMAL(19, 2) NOT NULL,
    FOREIGN KEY (cuenta_id) REFERENCES cuenta(id)
);