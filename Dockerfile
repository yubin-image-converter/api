# 1단계: 빌드
FROM gradle:7.6-jdk17 AS builder
WORKDIR /app

# ✅ 먼저 필요한 파일만 복사해서 캐시
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

RUN gradle --no-daemon build || true  # 캐시용 빌드 실패는 무시

# ✅ 그다음 소스 복사
COPY . .

# ✅ 실제 빌드 (이제 소스 다 있음)
RUN gradle clean bootJar -x test --no-daemon

# 2단계: 런타임
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
