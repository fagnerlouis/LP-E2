# Projeto 2 — CRUD Música (JavaFX + MySQL)

Entidades: **Arma**, **Cidade**, **Personagem** (com `id` PK).

## Banco
```sql
CREATE DATABASE IF NOT EXISTS projeto2 CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE projeto2;

CREATE TABLE IF NOT EXISTS musica_arma (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  modelo VARCHAR(120) NOT NULL,
  qtd_tiros INT NOT NULL,
  dono VARCHAR(120) NOT NULL
);

CREATE TABLE IF NOT EXISTS musica_cidade (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(120) NOT NULL,
  regiao VARCHAR(120) NOT NULL,
  violenta BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS musica_personagem (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(120) NOT NULL,
  origem VARCHAR(120) NOT NULL,
  vivo BOOLEAN NOT NULL
);

```

Configure `src/main/resources/db.properties` e rode:

```bash
mvn clean javafx:run
```
