CREATE TABLE autuacao (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    veiculo_id BIGINT NOT NULL,
    descricao TEXT NOT NULL,
    valor_multa DECIMAL(10, 2) NOT NULL,
    data_ocorrencia DATETIME NOT NULL
);

ALTER TABLE autuacao ADD CONSTRAINT fk_autuacao_veiculo
FOREIGN KEY (veiculo_id) REFERENCES veiculo (id);