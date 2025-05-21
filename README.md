# API Server (Spring Boot)

이 프로젝트는 이미지 변환 서비스를 위한 핵심 백엔드입니다. 사용자의 이미지 업로드 요청을 처리하고, 변환 상태를 관리하며, 최종 결과 파일을 제공합니다. Rust 기반 워커와는 RabbitMQ를 통해 통신하며, 상태 관리 및 데이터 저장을 위해 Redis와 PostgreSQL을 함께 사용합니다.


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
* Redis (worker 와 nfs 경로공유)
* RabbitMQ (비동기 메시징 처리)
* Swagger (API documentation)
* Gradle  (빌드 도구)

---

## Features

| Area               | Description                                                                 |
| ------------------ | --------------------------------------------------------------------------- |
| 이미지 변환   | 이미지 업로드를 처리하고 상태를 관리하며, 워커에게 RabbitMQ로 변환 요청 전송 |
| 인증 연동   | 외부 인증 서버로 발급한 JWT를 전달                             |
| 사용자 정보| 현재 로그인한 사용자 정보 및 권한 조회 (USER / ADMIN)        |          
| 콜백 처리  |워커로부터 변환 결과를 HTTP POST로 수신                    |
| 예외 처리 | GlobalExceptionHandler를 통한 일관된 에러 응답 처리|

---

## Project Structure 

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
# PostgreSQL, Redis, RabbitMQ는 로컬 또는 Docker로 실행되어 있어야 합니다.

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

## Testing(구현예정)

- JUnit 5와 Mockito 기반 단위 테스트 작성
- GitHub Actions CI 파이프라인에서 자동 실행

---

## Author

**Yubin Shin**
Backend, Messaging & Security Architecture
