CREATE TABLE Cliente (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE
);

CREATE TABLE Conta (
    id BIGSERIAL PRIMARY KEY,
    agencia VARCHAR(10) NOT NULL,
    numero_conta VARCHAR(20) NOT NULL UNIQUE,
    saldo NUMERIC(10, 2) NOT NULL,
    cliente_id BIGINT NOT NULL,
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);
