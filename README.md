# Product Management API

## Descrição
API RESTful para gerenciar produtos, permitindo criar, listar, recuperar, atualizar e excluir.

## Tecnologias Utilizadas
- Java 17
- Spring Boot
- Maven
- MySQL
- Docker Compose
- Swagger/OpenAPI

## Pré-requisitos
- Java 17
- Maven
- Docker
- Docker Compose

## Configuração do Banco de Dados
A aplicação utiliza MySQL como banco de dados. Um arquivo `docker-compose.yml` está disponível na pasta `src/main/resources` para facilitar a configuração do banco de dados.

## Subindo a Aplicação

1. Clone o repositório:
    ```bash
    git clone <URL_DO_REPOSITORIO>
    cd <NOME_DO_REPOSITORIO>
    ```

2. Inicie o container de banco de dados MySQL usando Docker Compose:
    ```bash
    docker-compose -f src/main/resources/docker-compose.yml up -d
    ```

3. Configure as propriedades do banco de dados no arquivo `application.yaml`:
   ```yaml
   spring:
       datasource:
           url: jdbc:mysql://localhost:3306/rd-product-db
           username: user
           password: password
   ```

4. Compile e execute a aplicação:
   ```bash
       mvn clean install
       mvn spring-boot:run
   ```

## Testando a Aplicação

### Testes Unitários
Para executar os testes unitários, utilize o comando:
   ```bash
   mvn test
   ```

### Testes de API com Postman
Uma coleção do Postman está disponível na pasta `src/main/resources` para facilitar os testes da API.

1. Importe a coleção `RDProduct.postman_collection.json` no Postman.
2. Certifique-se de que a aplicação está rodando em `http://localhost:8080`.
3. Execute as requisições da coleção para testar os endpoints da API.

## Endpoints Principais
- **POST /products**: Cria um novo produto.
- **GET /products**: Lista todos os produtos.
- **GET /products/{id}**: Retorna um produto pelo ID.
- **PUT /products/{id}**: Atualiza um produto existente.
- **DELETE /products/{id}**: Exclui um produto pelo ID.