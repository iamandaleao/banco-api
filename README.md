# 🏦 API de Gestão Bancária

Sistema backend para gerenciamento de operações bancárias desenvolvido com Java e Spring Boot, com foco em boas práticas de desenvolvimento e arquitetura em camadas.

## 📋 Sobre o Projeto

Este projeto foi desenvolvido como parte do meu portfólio de backend, demonstrando conhecimentos em:
- Desenvolvimento de APIs RESTful
- Persistência de dados com JPA/Hibernate
- Validações de negócio
- Integração com banco de dados PostgreSQL
- Arquitetura em camadas (Model, Repository, Service, Controller)

## 🚀 Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.7** - Framework para desenvolvimento de aplicações Java
- **Spring Data JPA** - Camada de persistência e acesso a dados
- **PostgreSQL** - Banco de dados relacional (Supabase)
- **Hibernate** - ORM (Object-Relational Mapping)
- **Lombok** - Redução de código boilerplate
- **Bean Validation** - Validações de entrada de dados
- **Maven** - Gerenciamento de dependências

## 📦 Funcionalidades Implementadas

### Gestão de Clientes
- ✅ Cadastro de clientes com validações
- ✅ Listagem de todos os clientes
- ✅ Busca de cliente por ID
- ✅ Busca de cliente por CPF
- ✅ Atualização de dados do cliente
- ✅ Exclusão de cliente
- ✅ Validação de CPF e email únicos

## 🏗️ Arquitetura

O projeto segue o padrão de arquitetura em camadas:

```
src/main/java/com/bancoapi/banco_api/
├── model/          # Entidades JPA (Cliente)
├── repository/     # Interfaces de acesso ao banco de dados
├── service/        # Regras de negócio
└── controller/     # Endpoints da API REST
```

### Camadas do Projeto

**Model (Entidades):**
- Representação das tabelas do banco de dados
- Anotações JPA para mapeamento objeto-relacional
- Validações de campos

**Repository:**
- Interface que estende JpaRepository
- Métodos customizados de consulta ao banco
- Abstração do acesso a dados

**Service:**
- Lógica de negócio da aplicação
- Validações complexas (CPF duplicado, email em uso)
- Orquestração entre repositories

**Controller:**
- Exposição dos endpoints REST
- Mapeamento de requisições HTTP
- Tratamento de respostas (status codes)

## 🔌 Endpoints da API

### Clientes

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/clientes` | Criar novo cliente |
| GET | `/api/clientes` | Listar todos os clientes |
| GET | `/api/clientes/{id}` | Buscar cliente por ID |
| GET | `/api/clientes/cpf/{cpf}` | Buscar cliente por CPF |
| PUT | `/api/clientes/{id}` | Atualizar dados do cliente |
| DELETE | `/api/clientes/{id}` | Excluir cliente |

### Exemplo de Requisição - Criar Cliente

**POST** `/api/clientes`

```json
{
  "nome": "Maria Silva",
  "cpf": "12345678901",
  "email": "maria@email.com",
  "telefone": "47999887766",
  "dataNascimento": "1995-05-20"
}
```

**Resposta (201 Created):**

```json
{
  "id": 1,
  "nome": "Maria Silva",
  "cpf": "12345678901",
  "email": "maria@email.com",
  "telefone": "47999887766",
  "dataNascimento": "1995-05-20"
}
```

## ⚙️ Como Executar o Projeto

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- Conta no Supabase (ou PostgreSQL local)

### Configuração

1. Clone o repositório
```bash
git clone <seu-repositorio>
cd banco-api
```

2. Configure o banco de dados em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://[SEU_HOST]:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=[SUA_SENHA]

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

3. Execute o projeto:

```bash
mvn spring-boot:run
```

4. A API estará disponível em: `http://localhost:8080`

## 🧪 Testando a API

Utilize ferramentas como Postman, Insomnia ou cURL para testar os endpoints.

**Exemplo com cURL:**

```bash
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "cpf": "98765432100",
    "email": "joao@email.com",
    "telefone": "11987654321",
    "dataNascimento": "1990-01-15"
  }'
```

## 📚 O Que Aprendi

Durante o desenvolvimento deste projeto, consolidei conhecimentos em:

- **Spring Boot:** Configuração, injeção de dependências, e criação de APIs REST
- **JPA/Hibernate:** Mapeamento objeto-relacional, relacionamentos e consultas
- **Validações:** Bean Validation para garantir integridade dos dados
- **Arquitetura:** Separação de responsabilidades em camadas
- **Boas Práticas:** Nomenclatura, organização de código e tratamento de exceções
- **PostgreSQL:** Modelagem de banco de dados relacional
- **Cloud Database:** Integração com Supabase

## 🎯 Próximos Passos

Funcionalidades planejadas para as próximas versões:

- [ ] Implementar entidade Conta Bancária
- [ ] Sistema de transferências entre contas
- [ ] Consulta de saldo e extrato
- [ ] Autenticação e autorização (Spring Security)
- [ ] Documentação da API com Swagger/OpenAPI
- [ ] Testes unitários e de integração
- [ ] Tratamento de exceções personalizado
- [ ] Paginação de resultados

## 📝 Status do Projeto

🚧 **Em desenvolvimento** - Versão inicial com CRUD de clientes implementado

## 👩‍💻 Autor

Desenvolvido como projeto de aprendizado e portfólio em desenvolvimento backend com Java e Spring Boot.

## 📄 Licença

Este projeto está sob a licença MIT.

---

⭐ Se você achou este projeto interessante, considere dar uma estrela no repositório!