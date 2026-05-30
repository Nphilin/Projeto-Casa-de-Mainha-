# 🏨 Hotel Casa de Mainha - API de Gerenciamento

Esta é a API de gerenciamento e controle do hotel **Casa de Mainha**, desenvolvida em Spring Boot.A plataforma foi reestruturada utilizando as melhores práticas de mercado, aplicando **Java Records** para a implementação de DTOs (Data Transfer Objects), validações de entrada, isolamento de camadas e uma cobertura de testes automatizados dividida em três níveis[cite: 4, 12, 16, 56, 101].

---

## 🚀 Tecnologias Utilizadas

***Java 17** [cite: 8, 56]
***Spring Boot 3.x** [cite: 8]
***Spring Data JPA** [cite: 21, 110]
***Banco de Dados H2** (Em memória para ambiente de testes e desenvolvimento) [cite: 21, 110]
* **Lombok**
***Jakarta Validation** (Bean Validation) [cite: 52, 81]
***Springdoc OpenAPI (Swagger)** (Documentação da API) [cite: 30, 217]
***JaCoCo** (Relatório de cobertura de código) [cite: 30, 217]

---

## 🛠️ Instruções de Setup e Execução

### Pré-requisitos
* Java 17 instalado e configurado no PATH.
*Maven instalado (ou utilize o wrapper `./mvnw` incluso no projeto).

### Como rodar a aplicação localmente

1. Clone o repositório para a sua máquina local:
   ```bash
   git clone [https://github.com/seu-usuario/casa-de-mainha.git]

   Navegue até a pasta raiz do projeto:

Bash
cd casa-de-mainha
Execute a aplicação utilizando o Maven Wrapper:

Bash
./mvnw spring-boot:run
A aplicação estará disponível em: http://localhost:8080

📋 Tabela de Endpoints (Módulo de Usuários)
Abaixo estão listadas as rotas da API referentes à gestão de usuários e níveis de acesso do hotel:  
PDF
+ 2

Verbo HTTP	Endpoint	Descrição	Status Code Sucesso	Status Code Erro
GET	/api/v1/usuarios	Lista todos os usuários cadastrados	200 OK	500 Internal
GET	/api/v1/usuarios/{id}	Busca os dados de um usuário pelo seu ID	200 OK	404 Not Found
POST	/api/v1/usuarios	Cadastra um novo usuário no sistema	201 Created	400 Bad Request
PUT	/api/v1/usuarios/{id}	Atualiza as informações de um usuário	200 OK	404 / 400
DELETE	/api/v1/usuarios/{id}	Remove permanentemente um usuário pelo ID	204 No Content	404 Not Found
💻 Exemplos de cURL para Testes Dinâmicos
Você pode testar os endpoints da API copiando e colando os comandos abaixo diretamente no seu terminal (com a aplicação rodando):  
PDF
+ 2

1. Criar um Novo Usuário (POST)
Bash
curl -X POST http://localhost:8080/api/v1/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "login": "admin_mainha",
    "senha": "senhaSegura123",
    "perfil": "ADMIN"
  }'
2. Listar Todos os Usuários (GET)
Bash
curl -X GET http://localhost:8080/api/v1/usuarios
3. Buscar Usuário por ID (GET)
Bash
curl -X GET http://localhost:8080/api/v1/usuarios/1
4. Atualizar Dados do Usuário (PUT)
Bash
curl -X PUT http://localhost:8080/api/v1/usuarios/1 \
  -H "Content-Type: application/json" \
  -d '{
    "login": "gerente_mainha",
    "senha": "novaSenha456",
    "perfil": "GERENTE"
  }'
5. Deletar um Usuário (DELETE)
Bash
curl -X DELETE http://localhost:8080/api/v1/usuarios/1
🧪 Execução de Testes e Cobertura (JaCoCo)
A suíte de testes do projeto foi construída seguindo a pirâmide de testes isolados:  
PDF
+ 1


Repository Tests: Validações de queries com @DataJpaTest e banco H2.  
PDF
+ 2


Service Tests: Validações de regras de negócio isoladas com Mockito puro.  
PDF
+ 2


Controller Tests: Validações de contratos HTTP, JSON e rotas com @WebMvcTest + MockMvc.  
PDF
+ 2

Para rodar todos os testes automatizados e gerar o relatório do JaCoCo, execute no terminal:  
PDF
+ 1

Bash
./mvnw test
Visualizar a Cobertura de Código (≥ 70%)
Após a execução do comando acima, abra o relatório gerado no seu navegador para auditar a cobertura de linhas:  
PDF
+ 2

Caminho do arquivo: target/site/jacoco/index.html

📖 Documentação da API (Swagger UI)
A API conta com documentação interativa e detalhada através do Swagger OpenAPI. Para visualizar os esquemas, payloads e realizar testes diretamente pela interface gráfica, acesse a URL após subir o projeto:  
PDF
+ 2

Swagger UI: http://localhost:8080/swagger-ui/index.html


6. **Salve o arquivo** (`Ctrl + S`).

Depois de salvar, o arquivo já vai ficar listado no painel de controle do seu Git (no VS Code) pronto para você fazer o *commit* e o *push* para o seu repositório oficial!