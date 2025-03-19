🚀 **Projeto Bank**

📌 **Descrição**
Este é um projeto desenvolvido em **Java com Spring Boot** que simula um sistema bancário simples. Ele permite a **criação de contas**, **depósitos**, **saques**, **transferências (PIX)** e **encerramento de contas**.

📂 **Estrutura do Projeto**
```
cpProjetoBank/
├── src/
│   ├── main/
│   │   ├── java/com/example/cpProjetoBank/
│   │   │   ├── controller/     # Controladores (Endpoints da API)
│   │   │   ├── model/          # Modelos de dados
│   │   │   ├── repository/     # Simula um banco de dados em memória
│   │   │   ├── service/        # Regras de negócio
│   │   ├── resources/
│   │   │   ├── application.properties  # Configurações do Spring Boot
│   │   │   ├── postman/        # Exemplos de requisições em JSON
├── pom.xml                     # Dependências do Maven
├── README.md                    # Documentação do projeto
```

🛠️ **Tecnologias Utilizadas**
- **Java 17**
- **Spring Boot**
- **Maven**
- **Postman** para testes

🚀 **Como Rodar o Projeto**
1. **Clonar o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/cpProjetoBank.git
   cd cpProjetoBank
   ```
2. **Rodar o projeto com Maven Wrapper:**
   ```bash
   ./mvnw spring-boot:run
   ```
(No Windows, use mvnw spring-boot:run sem ./)
(Se estiver no macOS/Linux e der erro de permissão, rode chmod +x mvnw antes)

📌 **Endpoints da API**
Aqui estão os principais endpoints do sistema:

| Função           | Método | URL |
|------------------|--------|--------------------------------|
| Criar Conta     | POST   | `/contas` |
| Buscar Contas   | GET    | `/contas` |
| Buscar Conta ID | GET    | `/contas/{id}` |
| Buscar CPF      | GET    | `/contas/cpf/{cpf}` |
| Encerrar Conta  | DELETE | `/contas/{id}` |
| Depósito       | POST   | `/contas/{id}/deposito?valor=500` |
| Saque          | POST   | `/contas/{id}/saque?valor=200` |
| Transferência PIX | POST   | `/contas/{origemId}/transferencias/{destinoId}?valor=100` |

📂 **Exemplos de Requisiões**
Os exemplos de JSON estão armazenados na pasta:
```
src/main/resources/postman/
```

📜 **Licença**
Projeto desenvolvido para fins acadêmicos e estudos.
