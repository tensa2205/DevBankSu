CREATE TABLE IF NOT EXISTS movimiento (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cuenta_id BIGINT NOT NULL,
    fecha DATE,
    tipo VARCHAR(255),
    valor DECIMAL(19, 2),
    saldo_disponible DECIMAL(19, 2),
    FOREIGN KEY (cuenta_id) REFERENCES cuenta(id)
);