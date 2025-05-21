# --- 1단계: 빌드 스테이지 ---
FROM gradle:7.6.4-jdk17-alpine AS builder
WORKDIR /app

# gradle wrapper 포함 전체 복사
COPY . .

# JAR 이름을 고정하려면 Gradle 스크립트도 수정된 상태여야 함
RUN ./gradlew clean build --no-daemon

# --- 2단계: 런타임 스테이지 ---
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# 빌드 결과물 복사 (이 경로가 실제 jar 생성 위치)
COPY --from=builder /app/build/libs/image-converter-api.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
