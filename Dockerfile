# 1단계: 빌드
FROM gradle:7.6-jdk17 AS builder
WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle

RUN gradle --no-daemon build || true

COPY . .

RUN gradle clean bootJar -x test --no-daemon

# 2단계: 런타임
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
