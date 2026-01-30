CREATE TABLE clientes (
      id bigint GENERATED ALWAYS AS IDENTITY,
      nome varchar(100) NOT NULL,
      email varchar(100) NOT NULL UNIQUE,
      senha varchar(255) NOT NULL,

      PRIMARY KEY(id)
);