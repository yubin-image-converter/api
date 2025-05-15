# 📦 API Server (Spring Boot)

Image Converter 프로젝트의 핵심 백엔드 서비스로, 이미지 업로드 요청을 수신하고 변환 상태를 관리하며 결과 파일을 제공합니다. 또한 RabbitMQ를 통해 Rust 워커와 메시지를 주고받고, Redis 및 PostgreSQL을 활용해 상태 및 데이터를 효율적으로 저장합니다.

---

## 🧰 기술 스택

* Java 17
* Spring Boot 3.x
* Spring Security + JWT 인증
* Spring Data JPA + PostgreSQL
* Redis (캐시 및 WebSocket 상태 공유)
* RabbitMQ (비동기 메시징)
* Swagger (API 문서)
* Gradle (빌드 도구)

---

## 🚀 주요 기능

| 영역       | 기능                                            |
| -------- | --------------------------------------------- |
| ✅ 이미지 변환 | 이미지 업로드 처리, 결과 경로 조회, 비동기 변환 요청 발송 (RabbitMQ) |
| ✅ 인증 연동  | 인증 서버에서 전달된 JWT 검증 및 사용자 인증 처리                |
| ✅ 유저 정보  | 유저 정보 조회 및 역할 확인 (USER / ADMIN)               |
| ✅ 콜백 처리  | Rust 워커로부터의 변환 완료 콜백 수신 및 결과 저장               |
| ✅ 예외 처리  | GlobalExceptionHandler 기반의 통일된 에러 응답 구조       |

---

## 📂 디렉토리 구조 (요약)

```bash
src/main/java/dev/yubin/imageconverter/api
├── auth           # 인증 연동 컨트롤러 및 DTO
├── common         # 예외 핸들러 및 유틸
├── config         # 전역 설정 (Redis, RabbitMQ, Security 등)
├── convert        # 이미지 변환 로직 및 컨트롤러
├── messaging      # RabbitMQ 발행/구독 처리
├── security       # JWT 필터 및 유저 정보
└── user           # 사용자 도메인 (Entity, Service, Repository)
```

---

## ⚙️ 실행 방법

### 로컬 개발

```bash
# PostgreSQL, Redis, RabbitMQ는 외부에서 동작해야 합니다 (또는 Docker로 별도 실행)

./gradlew build
java -jar build/libs/api-*.jar
```

### 주요 포트

* API 서버: `8080`
* Swagger UI: [`http://localhost:8080/api/swagger-ui.html`](http://localhost:8080/api/swagger-ui.html)

---

## 🔐 환경 변수 예시 (`.env`)

```dotenv
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

JWT_SECRET=your_jwt_secret_here
JWT_EXPIRATION_MS=604800000

NFS_ROOT=../uploads
STATIC_LOCATIONS=file:../uploads/

```

---

## 📬 API 문서

SpringDoc 기반 Swagger 문서 제공:
👉 [`/swagger-ui.html`](https://api.image-converter.yubinshin.com/api/swagger-ui/index.html)

---

## ✅ 테스트

* JUnit 5, Mockito 기반 테스트 작성
* GitHub Actions CI 파이프라인에서 자동 수행

---

## ✍️ 작성자

**신유빈 (Yubin Shin)**

* Backend, Spring Security, Messaging Architecture 설계 및 구현 담당
