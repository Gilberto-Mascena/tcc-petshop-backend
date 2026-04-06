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
- **Camada de DTO (Data Transfer Objects)**: Implementada com classes e Lombok para tráfego seguro de dados.
- **Value Objects (@Embeddable)**: A classe `Endereco` é mapeada como um objeto de valor embutido na entidade `Tutor`.
- **Tratamento Global de Exceções**: Uso de `@ControllerAdvice` para retornos de erro semânticos (404, 400, 422).
- **Injeção de Dependências**: Gerenciada pelo Spring IoC.

## 📖 Documentação da API (Swagger)
A API conta com documentação interativa via Swagger/OpenAPI. Com a aplicação rodando, acesse:
👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## 🛠️ Como Rodar o Projeto

### 1. Clonar o repositório
```bash 
git git clone https://github.com/Gilberto-Mascena/tcc-petshop-backend.git
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

| **Método** | **Endpoint** | **Descrição** |
| :---: | :---: | :---: |
| **POST** | `/api/pets` | Cadastra um novo Pet |
| **GET** | `/api/pets` | Lista todos os Pets |
| **GET** | `/api/pets/{id}` | Busca um Pet por ID |
| **PUT** | `/api/pets/{id}` | Atualiza um Pet por ID |
| **DELETE** | `/api/pets/{id}` | Deleta um Pet por ID |

### 📄 Licença
Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE.md) para detalhes.