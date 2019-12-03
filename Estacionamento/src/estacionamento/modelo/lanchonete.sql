CREATE DATABASE lanchonete;

USE lanchonete;

CREATE TABLE venda(
id INT PRIMARY KEY AUTO_INCREMENT,
total DECIMAL(10,2));

CREATE TABLE item_de_venda(
id INT PRIMARY KEY AUTO_INCREMENT,
id_venda INT,
quantidade INT,
id_produto INT,
FOREIGN KEY(id_venda) REFERENCES venda(id));

CREATE TABLE produto(
id INT PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(250),
valor_unitario DECIMAL(9,2),
imposto DECIMAL(3,2));

ALTER TABLE item_de_venda ADD FOREIGN KEY(id_produto) REFERENCES produto(id);

INSERT INTO produto (nome, valor_unitario, imposto) VALUES
('Salgado', 2.50, 0.10),
('Bolo', 2.50, 0.10),
('Refrigerante', 3.00, 0.15),
('Café', 1.00, 0.05),
('Suco', 1.50, 0.05);

ALTER TABLE venda ADD cpf VARCHAR(15) NULL;