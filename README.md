# 🐾 Petshop API - Backend

Projeto desenvolvido para o TCC do 5º período de Ciência da Computação. Esta é uma API RESTful para gerenciamento completo de um Petshop, focada na gestão de **Tutores** e seus **Pets**, utilizando práticas modernas de desenvolvimento Java.

## 🚀 Tecnologias Utilizadas
* **Java 21** (LTS)
* **Spring Boot 3.4.3**
* **Spring Data JPA** (Persistência)
* **PostgreSQL** (Banco de dados relacional)
* **Lombok** (Produtividade e código limpo)
* **Jakarta Validation** (Validação de dados)
* **Docker & Docker Compose** (Containerização do banco)
* **Dotenv Java** (Gestão de variáveis de ambiente)

## 🏗️ Arquitetura e Padrões
O projeto segue uma arquitetura em camadas, garantindo separação de responsabilidades:
- **Separação em Camadas**: Controller, Service, Repository e Entity.
- **Camada de DTO**: Isolamento total entre o modelo de banco de dados e o modelo de resposta da API
- **Value Objects (@Embeddable)**: Modelagem semântica da classe Endereco dentro da entidade Tutor.
- **Tratamento Global de Exceções**: Uso de @ControllerAdvice para retornos de erro padronizados (404, 400, 422).
- **Injeção de Dependências**: Gerenciada pelo Spring IoC.
- **Regras de Negócio**: Validação rigorosa de unicidade de CPF e consistência de dados relacionais.

## 🚧 Status do Desenvolvimento
O projeto está em fase ativa de desenvolvimento. Acompanhe o progresso detalhado no nosso [Kanban](https://github.com/users/Gilberto-Mascena/projects/2/views/1).

- **[➡️] Módulo de Serviços**: Em fase de implementação (Entidades, DTOs e Repositories concluídos).
- **[ ] Agendamento**: Lógica para controle de horários.
- **[ ] Autenticação**: Spring Security + JWT.

## 📊 Gestão do Projeto
Para acompanhar o desenvolvimento das funcionalidades, bugs e melhorias, utilizamos um quadro Kanban público no GitHub Projects:
👉 **[Quadro de Atividades (Kanban)](https://github.com/users/Gilberto-Mascena/projects/2/views/1)**

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

| **Método** | **Endpoint** | **Descrição** |
| :---: | :---: | :---: |
**POST** | `/api/tutores` |	Cadastra Tutor (Valida CPF único e Endereço) |
**GET** | `/api/tutores`| Lista todos os Tutores cadastrados |
**GET** | `/api/tutores/{id}`| Busca um Tutor detalhado por ID |
**PUT** | `/api/tutores/{id}` | Atualiza dados (Valida CPF se alterado) |
**DELETE**       | `/api/tutores/{id}` | Remove Tutor e seus vínculos |

**Pets**

| **Método** | **Endpoint** | **Descrição** |
| :---: | :---: | :---: |
| **POST** | `/api/pets` | Cadastra um novo Pet |
| **GET** | `/api/pets` | Lista todos os Pets |
| **GET** | `/api/pets/{id}` | Busca um Pet por ID |
| **PUT** | `/api/pets/{id}` | Atualiza um Pet por ID |
| **DELETE** | `/api/pets/{id}` | Deleta um Pet por ID |

### 📄 Licença
Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE.md) para detalhes.