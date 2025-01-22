
CREATE TABLE cuentas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_cuenta VARCHAR(255) NOT NULL UNIQUE,
    tipo_cuenta VARCHAR(255),
    saldo_inicial DOUBLE,
    estado BOOLEAN,
    cliente_id VARCHAR(255) NOT NULL
);

CREATE TABLE movimientos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE,
    tipo_movimiento VARCHAR(255),
    valor DOUBLE,
    saldo DOUBLE,
    cuenta_id BIGINT NOT NULL,
    CONSTRAINT fk_movimientos_cuentas FOREIGN KEY (cuenta_id) REFERENCES cuentas (id)
);
