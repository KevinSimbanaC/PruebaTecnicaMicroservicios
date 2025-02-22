-- Crear tabla 'cuentas' solo si no existe
CREATE TABLE IF NOT EXISTS cuentas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_cuenta VARCHAR(255) NOT NULL UNIQUE,
    tipo_cuenta VARCHAR(255),
    saldo_inicial DOUBLE,
    estado BOOLEAN,
    cliente_id VARCHAR(255) NOT NULL
);

-- Crear indice en 'numero_cuenta' solo si no existe
CREATE INDEX IF NOT EXISTS idx_numero_cuenta ON cuentas (numero_cuenta);

-- Crear tabla 'movimientos' solo si no existe
CREATE TABLE IF NOT EXISTS movimientos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE,
    tipo_movimiento VARCHAR(255),
    valor DOUBLE,
    saldo DOUBLE,
    cuenta_id BIGINT NOT NULL,
    CONSTRAINT fk_movimientos_cuentas FOREIGN KEY (cuenta_id) REFERENCES cuentas (id)
);

-- Crear índice en 'fecha' solo si no existe
CREATE INDEX IF NOT EXISTS idx_fecha ON movimientos (fecha);
