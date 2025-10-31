# 🚀 Performance Evaluation System

Este projeto é um serviço de backend Spring Boot responsável por gerenciar avaliações de performance, consolidando detalhes de desafios e comportamentos de colaboradores.

## 🎯 1. Arquitetura do Projeto

A arquitetura está estruturada sob o padrão **Model-View-Controller (MVC)**, comum em aplicações Spring. O objetivo é isolar a lógica de negócio, persistência de dados e o controle de fluxo.

### Camadas Principais e Suas Responsabilidades:

| Camada | Padrão | Responsabilidade |
| :--- | :--- | :--- |
| **Controller** | View/Controller | Receber requisições HTTP e retornar DTOs (Data Transfer Objects). |
| **Usecase/Service** | Model (Lógica) | Contém as regras de negócio complexas (e.g., cálculo de médias), orquestra operações e define o limite transacional (`@Transactional`). |
| **Repository** | Model (Persistência) | Utiliza o Spring Data JPA para interagir diretamente com o banco de dados. |
| **Model/Entity** | Model (Dados) | Representação da estrutura de dados no banco (JPA Entities). |
| **Mapper** | Auxiliar | Classes para conversão segura entre Entidades e DTOs, ou para construção de objetos de domínio. |

### Design Decisions:

* **Lombok (@Slf4j):** Usado para logging limpo em todas as classes de serviço.
* **Mapeamento Explícito:** A conversão entre `Entity` e `Response DTO` é feita manualmente, garantindo controle total sobre o formato de saída dos dados.

## 💾 2. Banco de Dados e Persistência

Essa api utiliza o **PostgreSQL** para persistência, gerenciado via Docker e Spring Data JPA/Hibernate.

### 2.1. Configuração do Docker Compose

O ambiente de banco de dados é inicializado através do Docker Compose para garantir isolamento e persistência de dados.

| Item | Configuração no `docker-compose.yml` |
| :--- | :--- |
| **Imagem** | `postgres:15-alpine` |
| **Database** | `POSTGRES_DB: performance_db` |
| **Porta** | `5432:5432` (Mapeada para o host) |
| **Volume** | `db_data` (Garante persistência dos dados no host) |

### 2.2. Connection Pool (HikariCP)

A aplicação utiliza o **HikariCP** como Connection Pool padrão.

* O Pool é configurado e ativado automaticamente pelo Spring Boot.
* O tamanho máximo do Pool é ajustado no `application.properties`.

> **Recomendação de Tuning (Exemplo):** Para um servidor de banco de dados com 4 núcleos de CPU, o `maximum-pool-size` ideal seria **10** (`4 x 2 + 2`).

## ⚙️ 3. Como Rodar a Aplicação

### 3.1. Pré-requisitos

* Java 17+
* Docker e Docker Compose
* Ferramenta de Build (Maven ou Gradle)

### 3.2. Passos para Inicialização

1.  **Subir o Banco de Dados (Docker Compose):**
    Na raiz do projeto (onde está o `docker-compose.yml`), execute:
    ```bash
    docker compose up -d
    ```
    Confirme que o container `performance-postgres` está `Up` (ativo) com `docker ps`.

2.  **Configuração da Aplicação (`application.properties`):**
    Verifique as credenciais de conexão:

    ```properties
    # Configuração JDBC
    spring.datasource.url=jdbc:postgresql://localhost:5432/performance_db
    spring.datasource.username=postgres
    spring.datasource.password=postgres

    # Hibernate DDL para criação automática de tabelas em Dev/Testes
    spring.jpa.hibernate.ddl-auto=update

    # Logs detalhados (Debug)
    logging.level.com.itau.performance_evaluation=DEBUG
    ```

3.  **Rodar o Spring Boot:**
    Execute a classe principal através do seu IDE ou usando o Maven Wrapper:
    ```bash
    ./mvnw spring-boot:run
    ```
    
4. **Configuração do PgAdmin:**
    Recomendo a instalação do PgAdmin para visualização das tabelas: `https://www.pgadmin.org/download/pgadmin-4-windows/`
    Fazer conexão com o banco `performance_db` usando as credenciais do `application.properties`.
    As tabelas serão criadas automaticamente na primeira execução da aplicação.

## 📝 4. Modelagem e Logs

### 4.1. Estrutura de Tabelas (DDL)

As tabelas são geradas pelo Hibernate (`ddl-auto=update`). O script base gerado é:

```sql
-- Tabela principal
CREATE TABLE tb_performance_assessment (
    id BIGSERIAL PRIMARY KEY,
    employee_id VARCHAR(255) NOT NULL,
    challenge_final_average DECIMAL(19, 2),
    behavior_final_average DECIMAL(19, 2)
);

-- Tabela de Detalhes de Desafio (com FK para Assessment)
CREATE TABLE tb_challenge_detail (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(255),
    score INT NOT NULL,
    assessment_id BIGINT NOT NULL, 
    CONSTRAINT fk_challenge_assessment FOREIGN KEY (assessment_id) REFERENCES tb_performance_assessment (id)
);

CREATE TABLE tb_behavioral_detail (
    id BIGSERIAL NOT NULL,
    description VARCHAR(255),
    score INT NOT NULL,

    assessment_id BIGINT NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_behavioral_assessment
        FOREIGN KEY (assessment_id)
        REFERENCES tb_performance_assessment (id)
        ON DELETE CASCADE
);
```

## 📝 4. Payloads de Exemplos

### Requisição (POST /behaviors)

Endpoint para criação de avaliação dos comportamentos;

**Exemplo de Payload:**

```json
{
  "employeeId": "5",
  "behaviors": [
    {
      "behavioral": "COLLAB",
      "grade": 5
    },
    {
      "behavioral": "LEARN",
      "grade": 2
    },
    {
      "behavioral": "DATA",
      "grade": 5
    },
    {
      "behavioral": "AUTONOMY",
      "grade": 5
    }
  ]
}
```

### Requisição (POST /challenges)

Endpoint para criação de avaliação dos desafios:

**Exemplo de Payload:**

```json
{
  "employeeId": "5",
  "challenges": [
    {
      "challenge": "AMBIENTE COLABORATIVO",
      "grade": 5
    },
    {
      "challenge": "APRENDE O TEMPO TODO",
      "grade": 2
    },
    {
      "challenge": "DIVERSIDADE",
      "grade": 5
    }
  ]
}
```

### Requisição (GET /{employeeId})

Endpoint para consulta das avaliações por colaborador;

 **Exemplo de Resposta:**

```json
{
  "data": {
    "employeeId": "5",
    "challengeFinalAverage": 4.0,
    "behaviorFinalAverage": 4.4,
    "challenges": [
      {
        "description": "AMBIENTE COLABORATIVO",
        "score": 5
      },
      {
        "description": "DIVERSIDADE",
        "score": 5
      },
      {
        "description": "APRENDE O TEMPO TODO",
        "score": 2
      }
    ],
    "behaviors": [
      {
        "description": "Você utiliza dados para tomar suas decisões?",
        "score": 5
      },
      {
        "description": "Você se atualiza e aprende o tempo todo?",
        "score": 2
      },
      {
        "description": "Você promove um ambiente colaborativo?",
        "score": 5
      },
      {
        "description": "Você trabalha com autonomia?",
        "score": 5
      }
    ]
  }
}
```