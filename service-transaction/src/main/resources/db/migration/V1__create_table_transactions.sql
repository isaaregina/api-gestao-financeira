CREATE TABLE transactions (
      id UUID PRIMARY KEY,
      cliente_id BIGINT NOT NULL,       -- casa com clienteId
      valor_original DECIMAL(19,2) NOT NULL, -- casa com valorOriginal
      moeda VARCHAR(3) NOT NULL,
      taxa_cambio DECIMAL(19,4),        -- casa com taxaCambio
      valor_convertido DECIMAL(19,2),   -- casa com valorConvertido
      tipo VARCHAR(20) NOT NULL,
      status VARCHAR(20) NOT NULL,
      data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP -- casa com dataCriacao
);

CREATE INDEX idx_transaction_cliente ON transactions(cliente_id);