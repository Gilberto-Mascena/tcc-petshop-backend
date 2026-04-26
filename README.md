# 🐾 Petshop API - Backend

Projeto desenvolvido para o TCC do 5º período de Ciência da Computação. Esta é uma API RESTful para gerenciamento completo de um Petshop, focada na gestão de **Tutores** e seus **Pets**, utilizando práticas modernas de desenvolvimento Java.

## 🚀 Tecnologias Utilizadas
* **Java 21** (LTS)
* **Spring Boot 3.4.3**
* **Spring Security & JWT** (Autenticação Stateless)
* **Spring Data JPA** (Persistência)
* **PostgreSQL** (Banco de dados relacional via Docker)
* **Lombok & Java Records** (Produtividade e imutabilidade)
* **Jakarta Validation** (Validação de dados)

## 🏗️ Arquitetura e Padrões
O projeto segue uma arquitetura em camadas, garantindo separação de responsabilidades:
- **Separação em Camadas**: Controller, Service, Repository e Entity.
- **Data Transfer Objects (Records)**: Isolamento entre o modelo de banco de dados e a resposta da API. Uso de Records para garantir imutabilidade e evitar recursividade JSON (Issue #4).
- **Value Objects (@Embeddable)**: Modelagem semântica da classe Endereco dentro da entidade Tutor.
- **Tratamento Global de Exceções**: Uso de `@ControllerAdvice` para retornos padronizados.
- **Segurança Stateless**: Proteção de endpoints via tokens JWT.

## 🚀 Status do Projeto

Acompanhe a evolução detalhada através do **[Quadro Kanban (GitHub Projects)](https://github.com/users/Gilberto-Mascena/projects/2/views/1)**.

### 📋 Roadmap de Funcionalidades

- **[✅] Gestão de Tutores e Pets**: Concluído (CRUD e validações base).
- **[✅] Segurança**: Implementada (JWT + Spring Security).
- **[➡️] Refatoração (Issue #4)**: Conversão de Classes para Records e ajuste de recursividade (Em progresso).
- **[ ] Tratamento de Erros (Issue #5)**: Mensagens amigáveis para violação de integridade (Ex: CPF duplicado).
- **[ ] Testes (Issue #6)**: Implementação de testes de integração com MockMvc.
- **[ ] Agendamento**: Controle de status e horários de serviços.

## 📖 Documentação da API (Swagger)
A API conta com documentação interativa via Swagger/OpenAPI. Com a aplicação rodando, acesse:
👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## 🛠️ Como Rodar o Projeto

### 1. Clonar o repositório
```bash 
git clone https://github.com/Gilberto-Mascena/tcc-petshop-backend.git
cd petshop-backend
```

### 2. Configurar Variáveis de Ambiente

O projeto utiliza um arquivo .env para proteger dados sensíveis.

1. Copie o arquivo de exemplo:

```bash
cp .env.example .env
```

2. Abra o .env e ajuste as credenciais (DB_URL, DB_USER, DB_PASSWORD) conforme seu ambiente.

### 3. Subir o Banco de Dados (Docker)

Inicie o container do PostgreSQL:
```bash
docker-compose up -d
```

### 4. Executar a Aplicação
```bash
./mvnw spring-boot:run
```

### 🛣️ Endpoints Principais

**Tutores & Endereços**

| **Método** |    **Endpoint**     |                 **Descrição**                 |
|:----------:|:-------------------:|:---------------------------------------------:|
|  **POST**  |   `/api/tutores`    | 	Cadastra Tutor (Valida CPF único e Endereço) |
|  **GET**   |   `/api/tutores`    |      Lista todos os Tutores cadastrados       |
|  **GET**   | `/api/tutores/{id}` |        Busca um Tutor detalhado por ID        |
|  **PUT**   | `/api/tutores/{id}` |    Atualiza dados (Valida CPF se alterado)    |
| **DELETE** | `/api/tutores/{id}` |         Remove Tutor e seus vínculos          |

**Pets**

| **Método** |   **Endpoint**   |     **Descrição**      |
|:----------:|:----------------:|:----------------------:|
|  **POST**  |   `/api/pets`    |  Cadastra um novo Pet  |
|  **GET**   |   `/api/pets`    |  Lista todos os Pets   |
|  **GET**   | `/api/pets/{id}` |  Busca um Pet por ID   |
|  **PUT**   | `/api/pets/{id}` | Atualiza um Pet por ID |
| **DELETE** | `/api/pets/{id}` |  Deleta um Pet por ID  |

### 📄 Licença
Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE.md) para detalhes.

### Gilberto | Dev _2026_