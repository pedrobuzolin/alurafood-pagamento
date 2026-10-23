# AluraFood — Microsserviço de Pagamentos

Microsserviço desenvolvido com **Java e Spring Boot** para gerenciar pagamentos na aplicação AluraFood. O projeto explora APIs REST, persistência de dados, integração entre serviços e comunicação assíncrona com RabbitMQ.

## Tecnologias

- Java 17
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- MySQL
- Flyway
- Bean Validation
- ModelMapper
- Spring Cloud OpenFeign
- Netflix Eureka Client
- RabbitMQ
- Resilience4j
- Maven

## Funcionalidades e conceitos

- Desenvolvimento de APIs REST para operações relacionadas a pagamentos.
- Persistência de dados com Spring Data JPA e MySQL.
- Gerenciamento de migrações do banco de dados com Flyway.
- Validação dos dados de pagamento com Bean Validation.
- Conversão entre entidades e DTOs com ModelMapper.
- Descoberta de serviços por meio do Netflix Eureka.
- Comunicação assíncrona utilizando RabbitMQ.
- Estudo de resiliência em chamadas entre serviços com Circuit Breaker.

## Comunicação assíncrona com RabbitMQ

O serviço utiliza RabbitMQ para publicar mensagens relacionadas aos pagamentos, permitindo que outros componentes recebam essas informações de forma assíncrona.

A configuração inclui:

- **Exchange `pagamentos.ex`:** exchange do tipo `fanout`, que distribui mensagens para as filas vinculadas.
- **`RabbitTemplate`:** componente utilizado para publicar mensagens.
- **Conversor JSON:** responsável pela conversão entre objetos Java e mensagens JSON.
- **`RabbitAdmin`:** inicialização e administração dos recursos AMQP configurados pela aplicação.

No fluxo atual, o microsserviço de pedidos possui um listener associado à fila `pagamentos.detalhes-pedido`, que consome as mensagens distribuídas pela exchange.

## Configuração

O RabbitMQ está configurado para utilizar `localhost:5672` no ambiente de desenvolvimento. O endereço do Eureka está definido como `http://localhost:8081/eureka`.

## Como executar

**Pré-requisitos:** Java 17, MySQL, RabbitMQ e o servidor Eureka em execução.

Clone o repositório:

```bash
git clone https://github.com/pedrobuzolin/alurafood-pagamento.git
cd alurafood-pagamento
```

Configure as variáveis de ambiente exigidas pela aplicação e confirme que os serviços externos estão disponíveis.

Execute no Windows:

```bash
mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

A aplicação utiliza `server.port=0`, permitindo que o Spring Boot selecione uma porta disponível. O endereço efetivo deve ser consultado nos logs de inicialização.

## Objetivo do projeto

Aprofundar conhecimentos em desenvolvimento backend com Java e Spring, explorando persistência de dados, integração entre microsserviços, mensageria assíncrona e mecanismos de resiliência em aplicações distribuídas.
