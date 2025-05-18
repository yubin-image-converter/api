# API Server (Spring Boot)

This is the core backend service for the Image Converter project. It receives image upload requests, manages conversion state, and provides result files. The server communicates with Rust-based workers through RabbitMQ and uses Redis and PostgreSQL for efficient state and data management.


---

## API Documentation

Swagger UI (SpringDoc):
[https://api.image-converter.yubinshin.com/swagger-ui/index.html](https://api.image-converter.yubinshin.com/swagger-ui/index.html)

---


## Technology Stack

* Java 17
* Spring Boot 3.x
* Spring Security + JWT
* Spring Data JPA + PostgreSQL
* Redis (for caching and WebSocket session sharing)
* RabbitMQ (asynchronous messaging)
* Swagger (API documentation)
* Gradle (build tool)

---

## Features

| Area               | Description                                                                 |
| ------------------ | --------------------------------------------------------------------------- |
| Image Conversion   | Handles image uploads, manages state, sends requests to worker via RabbitMQ |
| Auth Integration   | Validates JWTs issued by external auth server                               |
| User Info          | Retrieves user data and roles (USER / ADMIN)                                |
| Callback Handling  | Accepts conversion results from worker via HTTP POST                        |
| Exception Handling | Consistent error response via GlobalExceptionHandler                        |

---

## Project Structure (summary)

```bash
src/main/java/dev/yubin/imageconverter/api
├── auth           # Auth controller and DTOs
├── common         # Exception handler and utilities
├── config         # Redis, RabbitMQ, Security, OpenAPI configs
├── convert        # Image conversion controller, service, DTOs
├── messaging      # RabbitMQ publisher/consumer
├── security       # JWT filter, user details
└── user           # User domain (entity, service, repository)
```

---

## How to Run

### Local Development

```bash
# PostgreSQL, Redis, and RabbitMQ must be running (locally or via Docker)

./gradlew build
java -jar build/libs/api-*.jar
```

### Ports

* API: `8080`
* Swagger UI: [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)

---

## Environment Variables Example (`.env`)

```env
SPRING_APPLICATION_NAME=image-converter-api
SPRING_PROFILES_ACTIVE=local

SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/image-converter
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres

SPRING_DATA_REDIS_HOST=localhost
SPRING_DATA_REDIS_PORT=6379
SPRING_DATA_REDIS_PASSWORD=

SPRING_RABBITMQ_HOST=localhost
SPRING_RABBITMQ_PORT=5672
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest

RABBITMQ_EXCHANGE=image.convert.exchange
RABBITMQ_QUEUE=image.convert.queue
RABBITMQ_ROUTING_KEY=image.convert.routingKey
RABBITMQ_RESULT_QUEUE=image.convert.result.queue

JWT_SECRET=this_is_a_fake_jwt_secret_for_local_testing_12345!
JWT_EXPIRATION_MS=604800000

NFS_ROOT=../uploads
STATIC_LOCATIONS=file:../uploads/

SERVER_PORT=8080
SERVER_SERVLET_CONTEXT_PATH=/api
```

---

## Testing

* Uses JUnit 5 and Mockito
* Automatically runs in GitHub Actions CI pipeline

---

## Author

**Yubin Shin**
Backend, Messaging & Security Architecture
