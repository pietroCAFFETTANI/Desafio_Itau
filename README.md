# Desafio Itau

Este é um projeto de uma API que gerencia transações financeiras e calcula estatísticas relacionadas a essas transações.
## Inspiração

O desafio deste projeto foi inspirado no repositório [Desafio Itau Vaga 99 Junior](https://github.com/rafaellins-itau/desafio-itau-vaga-99-junior).

## Ferramentas Utilizadas

- **Java** ☕: Linguagem de programação utilizada no desenvolvimento do projeto.
- **Spring Boot** 🚀: Framework utilizado para desenvolvimento da aplicação.
- **Spring Actuator** ⚙️: Usado para monitoramento da aplicação, incluindo endpoints de health e metrics.
- **Swagger** 📜: Utilizado para gerar e documentar a API.
- **Testes Unitários** 🧪: Implementados com JUnit e Mockito para garantir o funcionamento correto da aplicação.


## Endpoints da API

### 1. Adicionar Transação
- **Método**: `POST`
- **URL**: `/transacao`
- **Descrição**: Este endpoint permite adicionar uma nova transação.
- **Requisição**:
    - **Corpo**: Deve ser enviado um objeto `TransacaoDTO` com o valor da transação e o horário.
    - **Exemplo**:
      ```json
      {
        "value": 100.0,
        "dateTime": "2025-03-09T10:15:30+00:00"
      }
    - **Regras**:
  
      | Nome do Valor | Tipo de Dados                                 | Requisição|
      |----------------|-----------------------------------------------|------------|
      | `value`        | `Double`  Não pode ser Negativo               | *Obrigatório*|
      | `dateTime`     | `OffsetDateTime` Não pode ser uma data futura | *Obrigatório*|
      

- **Respostas**:
    - **201**: Transação gravada com sucesso.
    - **422**: Campos não atendem os requisitos da transação.
    - **400**: Erro de requisição.
    - **500**: Erro interno.

---

### 2. Deletar Transações
- **Método**: `DELETE`
- **URL**: `/transacao`
- **Descrição**: Este endpoint permite deletar todas as transações registradas.
- **Respostas**:
    - **200**: Transações deletadas com sucesso.
    - **400**: Erro de requisição.
    - **500**: Erro interno.

---

### 3. Obter Estatísticas de Transações
- **Método**: `GET`
- **URL**: `/estatistica`
- **Descrição**: Este endpoint retorna estatísticas sobre as transações dentro de um intervalo de tempo.
- **Parâmetros**:
    - **time** (opcional): Intervalo de tempo em segundos. O valor padrão é 60 segundos.

- **Respostas**:
    - **200**: Estatísticas recuperadas com sucesso.
    - **400**: Erro na busca das estatísticas.
    - **500**: Erro interno.

- **Exemplo de resposta**:
  ```json
  {
    "count": 5,
    "sum": 500.0,
    "avg": 100.0,
    "min": 50.0,
    "max": 150.0
  }


## Testes Unitários

A aplicação inclui testes unitários para garantir o funcionamento correto dos serviços e controladores. Os testes são escritos utilizando o framework **JUnit 5** e a biblioteca **Mockito** para simulação de dependências.

### Testes Implementados

1. **Testes para o `ApiService`**:
    - Verificação de adição de transações com valores válidos e inválidos.
    - Teste para garantir que transações com data futura ou valor negativo sejam corretamente rejeitadas com exceção `UnprocessableEntity`.

2. **Testes para o `EstatisticaService`**:
    - Teste para calcular corretamente as estatísticas (como soma, média, valor mínimo e máximo) com um conjunto de transações dentro do intervalo de tempo especificado.

3. **Testes para o `ApiController`**:
    - Verificação das respostas HTTP dos endpoints de adicionar transação, deletar transações e obter estatísticas.
    - Simulação de diferentes cenários de sucesso e falha para cada endpoint.

### Como Executar os Testes

Para rodar os testes unitários, execute o seguinte comando no terminal:

```bash
mvn test
