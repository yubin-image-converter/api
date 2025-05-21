# --- 빌드 스테이지 (alpine ❌ → 일반 리눅스 베이스)
FROM gradle:7.6.4-jdk17 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew clean build --no-daemon

# --- 런타임 스테이지
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/image-converter-api.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
