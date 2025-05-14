# 1단계: 빌드
FROM gradle:7.6-jdk17 AS builder
WORKDIR /app

# ✅ 의존성 캐시용: Gradle 설정 먼저 복사
COPY build.gradle settings.gradle ./
RUN gradle dependencies --no-daemon

# ✅ 전체 복사 (resources 포함)
COPY . .

# ✅ 테스트 생략하고 fat jar 생성
RUN gradle bootJar -x test --no-daemon

# 2단계: 런타임
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
