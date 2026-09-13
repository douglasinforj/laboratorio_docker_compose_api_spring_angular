-- Cria o banco e tabela de usuário
CREATE TABLE IF NOT EXISTS usuarios (
    id        BIGSERIAL PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    email     VARCHAR(150) NOT NULL UNIQUE,
    telefone  VARCHAR(20),
    criado_em TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Seed inicial para validar conexão
INSERT INTO usuarios (nome, email, telefone) VALUES ('Admin Teste', 'admin@lab.com', '21999990000');