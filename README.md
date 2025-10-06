# Projeto DimDim - Aplicações e Banco em Nuvem (FIAP)

## 📖 Descrição do Projeto

Este repositório contém o projeto **DimDim**, uma API REST para um banco digital fictício. O projeto foi desenvolvido como parte do 5º Checkpoint da disciplina de **DevOps Tools & Cloud Computing** do curso de Análise e Desenvolvimento de Sistemas da FIAP.

A aplicação foi construída em Java com o framework Spring Boot e implantada na nuvem da **Microsoft Azure**, utilizando um Serviço de Aplicativo (Web App) e um Banco de Dados do Azure para PostgreSQL como serviço (PaaS).

---

## 👥 Integrantes do Grupo

| Nome Completo     | RM      |
| ----------------- | ------- |
| `Carolina Estevam Rodgerio` | `554975` |
| `Enrico Andrade D'Amico` | `557706` |
| `Lucas Thalles dos Santos` | `558886` |

---

## 🚀 Link da Aplicação na Nuvem

A aplicação está implantada e pode ser acessada através da seguinte URL:

**`[https://[NOME_DO_SEU_APP].azurewebsites.net](https://dimdim-app-554975-cpcqgaeug7e9fuct.brazilsouth-01.azurewebsites.net)`**

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17
* **Framework:** Spring Boot
* **Banco de Dados:** PostgreSQL (Banco de Dados do Azure para PostgreSQL)
* **Plataforma Cloud:** Microsoft Azure
    * Serviço de Aplicativo (Web App)
    * Banco de Dados do Azure para PostgreSQL
    * Application Insights (para monitoramento)
* **Build e Dependências:** Apache Maven
* **IDE:** Visual Studio Code

---

## ⚙️ Como Realizar a Implantação do Projeto (How-To Completo)

Este guia descreve os passos para implantar esta aplicação no seu próprio ambiente Azure.

### Pré-requisitos

* Conta ativa no Microsoft Azure
* Java Development Kit (JDK) 17 ou superior
* Apache Maven 3.6+ configurado nas variáveis de ambiente
* Visual Studio Code com as extensões "Extension Pack for Java" e "Azure App Service"
* Git instalado

### Passo 1: Criar os Recursos no Portal Azure

1.  **Criar o Banco de Dados (PostgreSQL):**
    * No Portal Azure, procure por "Banco de Dados do Azure para PostgreSQL" e crie um **Servidor Flexível**.
    * Configure um nome para o servidor, um usuário e senha de administrador.
    * Na aba "Rede", habilite o "Acesso Público" e adicione uma regra de firewall para permitir o acesso do seu IP atual.
    * Anote o nome do servidor, o usuário e a senha.

2.  **Criar o Serviço de Aplicativo (Web App):**
    * No Portal Azure, procure por "Serviços de Aplicativos" e clique em "Criar".
    * **Grupo de Recursos:** Crie um novo ou use o mesmo do banco de dados.
    * **Nome:** Dê um nome globalmente único para a aplicação.
    * **Pilha de runtime:** Selecione **Java 17**.
    * **Servidor Web Java:** Selecione **Java SE (Incorporado)**.
    * **Sistema Operacional:** Linux.
    * **Plano do Serviço de Aplicativo:** Crie um novo plano e escolha o tier **F1 Gratuito (Free)**.
    * **Monitoramento:** Na aba "Monitoramento", habilite o **Application Insights**.

### Passo 2: Configurar e Fazer o Deploy do Código

1.  **Clonar o Repositório:**
    ```sh
    git clone https://github.com/carolrodgerio/dimdim.git
    cd dimdim
    ```

2.  **Configurar a Conexão com o Banco:**
    * Abra o projeto no VS Code.
    * Navegue até `src/main/resources/application.properties`.
    * Altere as seguintes linhas com as credenciais do seu banco de dados criado no passo 1:
        ```properties
        spring.datasource.url=jdbc:postgresql://[NOME_DO_SEU_SERVIDOR][.postgres.database.azure.com:5432/postgres?sslmode=require](https://.postgres.database.azure.com:5432/postgres?sslmode=require)
        spring.datasource.username=[SEU_USUARIO_ADMIN]
        spring.datasource.password=[SUA_SENHA]
        ```

3.  **Fazer o Deploy via VS Code:**
    * Com a extensão "Azure App Service" instalada e logada, clique no ícone do Azure na barra lateral.
    * Na seção "App Service", encontre o ícone de seta azul para cima ("Deploy to Web App...").
    * O VS Code irá perguntar:
        1.  A pasta a ser implantada (confirme a pasta `dimdim`).
        2.  Sua assinatura do Azure.
        3.  O Web App de destino (selecione o que você criou no portal).
    * Confirme o deploy e aguarde a conclusão do processo.

---

## 🗄️ Estrutura do Banco de Dados (DDL)

O projeto utiliza duas tabelas com relacionamento master-detail (`Cliente` e `Conta`).

```sql
CREATE TABLE Cliente (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE
);

CREATE TABLE Conta (
    id BIGSERIAL PRIMARY KEY,
    agencia VARCHAR(10) NOT NULL,
    numero_conta VARCHAR(20) NOT NULL UNIQUE,
    saldo NUMERIC(10, 2) NOT NULL,
    cliente_id BIGINT NOT NULL,
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);
```

---

## 🔌 Exemplos de Uso da API (JSON das Operações)

A seguir, exemplos de como interagir com a API usando `curl`.

### 1. Criar um novo Cliente

* **Comando:**
    ```sh
    curl -X POST -H "Content-Type: application/json" -d '{
        "nome": "Steve Jobs",
        "cpf": "111.222.333-44"
    }' https://[NOME_DO_SEU_APP].azurewebsites.net/api/clientes
    ```

### 2. Listar todos os Clientes

* **Comando:**
    ```sh
    curl -X GET https://[NOME_DO_SEU_APP].azurewebsites.net/api/clientes
    ```

### 3. Criar uma Conta para um Cliente (ID = 1)

* **Comando:**
    ```sh
    curl -X POST -H "Content-Type: application/json" -d '{
        "agencia": "0001",
        "numeroConta": "12345-6",
        "saldo": 1000000.00
    }' https://[NOME_DO_SEU_APP].azurewebsites.net/api/contas/cliente/1
    ```

### 4. Atualizar um Cliente (ID = 1)

* **Comando:**
    ```sh
    curl -X PUT -H "Content-Type: application/json" -d '{
        "nome": "Steve Jobs Updated",
        "cpf": "111.222.333-44"
    }' https://[NOME_DO_SEU_APP].azurewebsites.net/api/clientes/1
    ```

### 5. Deletar um Cliente (ID = 1)

* **Comando:**
    ```sh
    curl -X DELETE https://[NOME_DO_SEU_APP].azurewebsites.net/api/clientes/1
    ```
