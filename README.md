# 🎵 Projeto 2 — CRUD Música (JavaFX + MySQL)

**Curso:** Banco de Dados — FATEC São José dos Campos  
**Disciplina:** Linguagem de Programação  
**Tecnologias:** Java 21, JavaFX 21.0.3, MySQL 8, Maven

---

## 🧩 Descrição do Projeto

Aplicação **CRUD** (Create, Read, Update, Delete) desenvolvida em **JavaFX**, com persistência de dados via **MySQL** e integração JDBC.  
O sistema implementa três entidades inter-relacionadas ao tema *“Música”*, adaptadas de um exercício anterior, com interface gráfica funcional e banco relacional.

**Entidades principais:**
- 🎯 **Arma** — representa um instrumento ou objeto com atributos `modelo`, `qtd_tiros` e `dono`.
- 🏙️ **Cidade** — contém informações de `nome`, `região` e se é `violenta`.
- 🧍 **Personagem** — representa uma pessoa/personagem com `nome`, `origem` e se está `vivo`.

Cada entidade possui seu próprio DAO e tabela no banco de dados, com operações completas de inserção, listagem, atualização e exclusão.

---

## 🖥️ Estrutura de Pastas

```
src/
 └── main/
     ├── java/
     │   ├── org/example/musicaapp/
     │   │   ├── Main.java
     │   │   ├── CrudController.java
     │   │   ├── Arma.java
     │   │   ├── Cidade.java
     │   │   ├── Personagem.java
     │   │   ├── db/DB.java
     │   │   └── dao/
     │   │       ├── ArmaDAO.java
     │   │       ├── CidadeDAO.java
     │   │       └── PersonagemDAO.java
     ├── resources/
     │   ├── org/example/musicaapp/crud-view.fxml
     │   ├── db.properties
     │   └── schema.sql
```

---

## 🧠 Funcionalidades

- CRUD completo (criar, listar, atualizar e excluir) para as três entidades.
- Persistência automática via **DAO pattern**.
- Interface JavaFX intuitiva com tabelas e campos de formulário.
- Criação automática do banco e tabelas (executando `schema.sql` ao iniciar).
- Feedback visual por meio de `Label` de status e mensagens de alerta.

---

## 🗃️ Banco de Dados

O arquivo `schema.sql` é executado automaticamente na primeira execução.

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

---

## ⚙️ Configuração

Edite `src/main/resources/db.properties` com as credenciais do seu MySQL:

```properties
db.host=localhost
db.port=3306
db.name=projeto2
db.user=root
db.password=
```

---

## ▶️ Execução

Certifique-se de ter o MySQL rodando e execute:

```bash
mvn clean javafx:run
```

> A aplicação criará o banco e tabelas automaticamente (via `DB.runSchema()`).

---

## 🧰 Dependências Principais

- `org.openjfx:javafx-controls`
- `org.openjfx:javafx-fxml`
- `com.mysql:mysql-connector-j`
- `org.openjfx:javafx-maven-plugin` (para execução direta via Maven)

---

## 📸 Interface

A tela principal (`crud-view.fxml`) contém três seções com abas:
- **Arma**
- **Cidade**
- **Personagem**

Cada aba possui:
- Campos de texto para cadastro/edição.
- Tabela com listagem em tempo real.
- Botões para criar, atualizar, excluir, limpar e executar método específico da entidade.

---

## 🧩 Arquitetura

| Camada | Arquivos | Função |
|--------|-----------|--------|
| **Model** | `Arma`, `Cidade`, `Personagem` | Representam as entidades e seus atributos |
| **DAO** | `ArmaDAO`, `CidadeDAO`, `PersonagemDAO` | Gerenciam operações SQL com o banco |
| **Controller** | `CrudController` | Controla a lógica da interface JavaFX |
| **View** | `crud-view.fxml` | Define a interface visual (FXML) |
| **Infra** | `DB.java`, `db.properties` | Configura e executa a conexão JDBC |

---

## 🚀 Pontos de Destaque

- **Execução automática do schema** → não precisa criar tabelas manualmente.
- **DAO isolado por entidade** → fácil manutenção e clareza de código.
- **JavaFX limpo e modular** → segue boas práticas (FXML + Controller).
- **Compatível com JDK 21 (LTS)** → ambiente estável e sem warnings.

---

## 👨‍💻 Desenvolvido por

**Fagner Louis Silva Nascimento**  
FATEC São José dos Campos — Prof. Jessen Vidal  
Curso: **Banco de Dados**  
Disciplina: **Linguagem de Programação**
