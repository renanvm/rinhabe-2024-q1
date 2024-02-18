CREATE TABLE Conta (
                       id INTEGER PRIMARY KEY,
                       limite INTEGER,
                       saldo INTEGER
);

INSERT INTO Conta (id, limite, saldo)
VALUES (1, 100000, 0),
       (2, 80000, 0),
       (3, 1000000, 0),
       (4, 10000000, 0),
       (5, 500000, 0);

CREATE TABLE Transacao (
                           id SERIAL PRIMARY KEY,
                           conta_id INTEGER,
                           valor INTEGER NOT NULL,
                           tipo VARCHAR(255) NOT NULL,
                           descricao VARCHAR(255),
                           realizada_em TIMESTAMP
);
