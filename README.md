# ProjetoFinalDSC

Antes de executar o projeto, certifique-se de ter instalado:
- Java 24
- Maven
- MySQL
- Insomnia ou Postman para testar a API

Configuração do Banco de Dados:

- Criar Database no MySql : create database Cinema;
- Configurar o application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/Cinema?useSSL=false&serverTimezone=UTC
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

Como Executar o Projeto

1. Abrir o terminal na raiz do projeto.
2. Compilar o projeto.
3. A API estará disponivel na rota http://localhost:8080

OBS:

Para testar as rotas privadas crie um usuário ADMIN através do MySQL, depois use a rota de trocar senha para que ela seja encriptada no banco de dados!

Ex: INSERT INTO tb_usuarios (nome, nome_usuario, email, senha, role)
VALUES ('Administrador Padrão', 'admin','admin@cinema.com', 'admin1234', 'ADMIN');
   
