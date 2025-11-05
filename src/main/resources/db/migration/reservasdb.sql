DROP TABLE IF EXISTS reserva CASCADE;
DROP TABLE IF EXISTS equipamento CASCADE;
DROP TABLE IF EXISTS professor CASCADE;

CREATE TABLE equipamento (
    idEquipamento INTEGER PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(1000),
    ativo BOOLEAN
);

CREATE TABLE professor (
    idProfessor INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE reserva (
    idReserva INTEGER PRIMARY KEY AUTO_INCREMENT,
    ID_EQUIPAMENTO INTEGER,
    ID_PROFESSOR INTEGER,
    sala VARCHAR(255),
    DATA_RETIRADA TIMESTAMP,
    DATA_ENTREGA TIMESTAMP,
    status VARCHAR(50),
    CONSTRAINT fk_reserva_equipamento FOREIGN KEY (ID_EQUIPAMENTO) REFERENCES equipamento(idEquipamento),
    CONSTRAINT fk_reserva_professor FOREIGN KEY (ID_PROFESSOR) REFERENCES professor(idProfessor)
);

-- Índices para chaves estrangeiras
CREATE INDEX idx_reserva_equipamento ON reserva(ID_EQUIPAMENTO);
CREATE INDEX idx_reserva_professor ON reserva(ID_PROFESSOR);