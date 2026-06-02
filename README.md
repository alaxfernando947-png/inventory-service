# 📦 Divenclasse - Inventory Service

> Microsserviço de gerenciamento de estoque desenvolvido para o projeto **Divenclasse – Plataforma de Moda Social**.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.0-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Docker](https://img.shields.io/badge/Docker-24+-2496ED)
![Swagger](https://img.shields.io/badge/OpenAPI-3-success)
![Tests](https://img.shields.io/badge/Tests-10%2F10_Passing-brightgreen)

---

## 📋 Informações do Projeto

| Item | Valor |
|--------|--------|
| Projeto | Divenclasse – Plataforma de Moda Social |
| Módulo | inventory-service |
| Descrição | Controle de estoque de roupas sociais masculinas e femininas |
| Porta | 8085 |
| Banco de Dados | PostgreSQL 16 |
| Framework | Spring Boot 3.3 |
| Linguagem | Java 17 |
| Build | Maven 3.9 |
| Containerização | Docker + Docker Compose |
| API Docs | Swagger/OpenAPI 3 |
| Testes | JUnit 5 + Mockito |
| Repositório | https://github.com/CahMuniz/Projeto_Arquitetura_Web |

---

## 🎯 Objetivo

O **inventory-service** é um microsserviço RESTful responsável pelo gerenciamento completo do estoque da plataforma Divenclasse.

Funcionalidades:

- Cadastro de produtos
- Atualização de estoque
- Consulta por filtros
- Controle de disponibilidade
- Validações de negócio
- Tratamento global de erros
- Documentação automática
- Testes unitários

---

# 🏗 Arquitetura

```text
controller/
service/
repository/
entity/
dto/
exception/
config/
```

## Camadas

### Controller

Responsável por receber requisições HTTP e encaminhar para a camada de serviço.

### Service

Contém todas as regras de negócio.

### Repository

Persistência dos dados usando Spring Data JPA.

### Entity

Mapeamento das tabelas do banco.

### DTO

Objetos de entrada e saída da API.

### Exception

Tratamento centralizado de erros.

### Config

Configuração do Swagger/OpenAPI.

---

# 👔 Entidade Product

| Campo | Tipo | Obrigatório | Descrição |
|---------|---------|---------|---------|
| id | Long | Auto | Gerado automaticamente |
| nome | String | ✅ | Nome do produto |
| descricao | String | Não | Descrição detalhada |
| categoria | Enum | ✅ | Categoria do produto |
| tamanho | String | ✅ | Tamanho |
| cor | String | ✅ | Cor principal |
| preco | BigDecimal | ✅ | Preço |
| quantidadeEstoque | Integer | ✅ | Quantidade disponível |
| marca | String | Não | Marca |
| genero | Enum | ✅ | Masculino/Feminino/Unissex |
| dataCadastro | LocalDateTime | Auto | Data de criação |
| dataAtualizacao | LocalDateTime | Auto | Atualizada automaticamente |

---

# 📦 Categorias

```text
TERNO
BLAZER
CAMISA_SOCIAL
CALCA_SOCIAL
GRAVATA
SAPATO_SOCIAL
CINTO
COLETE
```

# 🚻 Gêneros

```text
MASCULINO
FEMININO
UNISSEX
```

---

# 🌐 Endpoints REST

| Método | Endpoint | Descrição |
|----------|----------|----------|
| POST | /products | Cadastrar produto |
| GET | /products | Listar produtos |
| GET | /products/{id} | Buscar por ID |
| GET | /products/search?name= | Buscar por nome |
| GET | /products/category/{c} | Buscar por categoria |
| GET | /products/size/{s} | Buscar por tamanho |
| GET | /products/gender/{g} | Buscar por gênero |
| GET | /products/low-stock | Estoque baixo |
| PUT | /products/{id} | Atualização completa |
| PATCH | /products/{id}/stock | Atualizar estoque |
| DELETE | /products/{id} | Excluir produto |

---

# ⚙️ Regras de Negócio

✅ Estoque nunca pode ficar negativo

✅ Campo `emEstoque` calculado automaticamente

✅ Alerta para estoque baixo (≤ 3)

✅ `dataCadastro` imutável

✅ `dataAtualizacao` automática

✅ Preço maior que zero

✅ Bean Validation em todos os campos obrigatórios

✅ Paginação em listagens

---

# 🚨 Tratamento de Erros

Exemplo:

```json
{
  "timestamp": "2025-05-27T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Produto não encontrado com ID: 5",
  "path": "/products/5"
}
```

## Exceções

| Exceção | HTTP |
|----------|----------|
| ProductNotFoundException | 404 |
| InsufficientStockException | 422 |
| MethodArgumentNotValidException | 400 |
| Exception | 500 |

---

# 🛠 Stack Tecnológica

| Tecnologia | Versão |
|------------|---------|
| Java | 17 |
| Spring Boot | 3.3.0 |
| Spring Data JPA | 3.3.0 |
| Hibernate | 6.x |
| PostgreSQL | 16 |
| Maven | 3.9 |
| Docker | 24+ |
| Docker Compose | 3.9 |
| Lombok | 1.18 |
| OpenAPI | 2.5.0 |
| Spring Actuator | 3.3.0 |
| JUnit | 5 |
| Mockito | 5 |
| H2 Database | 2.x |

---

# 📁 Estrutura do Projeto

```text
inventory-service
│
├── src
│   ├── main
│   │   ├── controller
│   │   ├── service
│   │   ├── repository
│   │   ├── entity
│   │   ├── dto
│   │   ├── exception
│   │   └── config
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

---

# 🧪 Testes Unitários

## Resultado

```text
10 tests passed
10 tests total

BUILD SUCCESS
```

### Tecnologias

- JUnit 5
- Mockito
- H2 Database

---

# 🐳 Docker

## Dockerfile

- Multi-stage build
- Imagem otimizada
- Healthcheck configurado
- Usuário sem privilégios

## Docker Compose

| Serviço | Porta |
|----------|---------|
| PostgreSQL | 5432 |
| Inventory Service | 8085 |

---

# 🚀 Executando o Projeto

## Subir os containers

```bash
docker compose up --build
```

## Swagger

```text
http://localhost:8085/swagger-ui.html
```

---

# 📨 Exemplo de Cadastro

## Request

```json
{
  "nome": "Terno Slim Azul Marinho",
  "descricao": "Terno slim fit em lã premium",
  "categoria": "TERNO",
  "tamanho": "48",
  "cor": "Azul Marinho",
  "preco": 1299.90,
  "quantidadeEstoque": 15,
  "marca": "Divenclasse",
  "genero": "MASCULINO"
}
```

## Response

```json
{
  "id": 1,
  "nome": "Terno Slim Azul Marinho",
  "categoria": "TERNO",
  "tamanho": "48",
  "cor": "Azul Marinho",
  "preco": 1299.90,
  "quantidadeEstoque": 15,
  "genero": "MASCULINO",
  "emEstoque": true
}
```

---

# ✅ Checklist

| Item | Status |
|--------|--------|
| Entidade Product | ✅ |
| 8 Categorias | ✅ |
| 11 Endpoints | ✅ |
| Paginação | ✅ |
| Bean Validation | ✅ |
| DTOs | ✅ |
| Exceções Customizadas | ✅ |
| Handler Global | ✅ |
| Swagger/OpenAPI | ✅ |
| Logs | ✅ |
| Testes Unitários | ✅ |
| Dockerfile | ✅ |
| Docker Compose | ✅ |
| Aplicação Rodando | ✅ |
| Swagger Acessível | ✅ |
| Java 17 | ✅ |

---

# 👥 Equipe

- Ryan Junio Pereira Costa
- Carolina Soares
- Gustavo Alves
- Victor Andrey
- Joao Victor Lima
- Alax Fernando

---

# 👨‍💻 Autor do Módulo

**Álax Fernando de Freitas Nunes**

Microsserviço desenvolvido para a disciplina de Arquitetura de Aplicações Web — Newton Paiva.
