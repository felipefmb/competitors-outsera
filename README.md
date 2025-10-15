# · Produções Cinematográficas

## 1. Visão Geral

API responsável por centralizar e gerenciar informações relacionadas às produções cinematográficas.

**Contexto de Negócio:**  
A aplicação permite a consulta de informações sobre produções cinematográficas, como as informações relacionadas a produtores, estúdios e as próprias produções.

---

## 2. Stack & Requisitos

### 2.1. Linguagem & Framework
- **Java:** 17
- **Spring Boot:** 3.5.6

### 2.2. Build & Container
- **Build:** Gradle
- **Containerização:** Docker

### 2.3. Persistência & Dados
- **Banco de dados:** H2
- **Leitura de arquivos:** Commons-CSV

### 2.4. Logging & Observabilidade
- **Log:** Logback
- **Métricas:** Micrometer + Prometheus

### 2.5. Documentação & Contrato
- **API Docs:** OpenAPI

### 2.6. Dependências Externas
- **Banco de dados:** H2

---

## 3. Arquitetura

### 3.1. Eventos
#### Estrutura dos Payloads
**Endpoint:** `[GET] /actuator/health/`
``` json
{"status":"UP"}
```

**Endpoint:** `[GET] /v1/golden-raspberry-awards/producers/winnerInfos`

```json
{
  "min": [
    {
      "producer": "Producer 01",
      "interval": 1,
      "previousWin": 2019,
      "followingWin": 2020
    }
  ],
  "max": [
    {
      "producer": "Producer 02",
      "interval": 17,
      "previousWin": 2020,
      "followingWin": 2037
    }
  ]
}
```
---

## 4. Contratos da API
### 4.1 Swagger UI
- **URL no navegador:** `/swagger-ui/index.html`

---

## 6. Configuração

- **Profiles disponíveis:** `local`, `dev`, `hom`, `prod`
- **Variáveis de ambiente:**

| CHAVE       | Descrição            | Exemplo                  | Obrigatória |
|-------------|----------------------|--------------------------|-------------|
| DB_URL      | URL do banco de dados | `jdbc:h2:mem:testdb`     | Sim         |
| SERVER_PORT | Porta da aplicação    | `8500`                   | Não         |  

---

## 7. Como Executar
### 7.1. Subir a aplicação
- **Instruções:** Acessar o root da aplicação via terminal e executar os comandos abaixo conforme a situação desejada.

**Profile local:**
```bash
./gradlew bootRun
```

**Profile dev, hom ou prod:**
```bash
./gradlew bootRun -Dspring.profiles.active=<profile>
```

**Via Docker (requer [Docker instalado](https://docs.docker.com/engine/install/)):**
```bash
./gradlew clean build
docker build -t <nome_imagem> .
docker run <nome_imagem> --spring.profiles.active=<profile>
```
Alternativamente ao docker, também pode ser utilizado via Docker Compose:
```bash
PROFILE=<profile> docker compose up
```

#### Obs.: Após realizar o build da aplicação, o relatório de cobertura de testes estará disponível em:
```
/build/reports/jacoco/test/html/index.html
```

---
### Verificando os resultados 
- Antes de executar a aplicação, alterar o arquivo que consta em: /src/main/resources/application/movielist.csv conforme desejado
- Executar a aplicação conforme as instruções anteriores e com a aplicação em estado de execução na máquina local, abrir o navegador de sua preferência e acessar a URL: http://localhost:8500/swagger-ui/index.html
- Localizar o endpoint [GET] /v1/golden-raspberry-awards/producers/winnerInfos
- Clicar no botão "Try out"
- Clicar no botão "Execute"
- Verificar o resultado da execução no campo "Response body"

---
### 8.3. Executar somente os testes
```bash
./gradlew test
```