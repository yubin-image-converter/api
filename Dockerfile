# 1) Build 스테이지: Gradle 이미지로 빌드
FROM gradle:7.6-jdk17 AS builder
WORKDIR /app

# 의존성 선언 파일 먼저 복사해서 캐시 활용
COPY build.gradle settings.gradle ./
# src 코드만 복사
COPY src/ src/

# 시스템에 내장된 gradle 명령어로 fat-jar 생성
RUN gradle clean bootJar -x test --no-daemon

# 2) Runtime 스테이지: 경량 JRE 사용
FROM eclipse-temurin:17-jre
WORKDIR /app

# 빌드 결과물 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 포트 노출 (application.yml 설정 포트와 맞춰주세요)
EXPOSE 8080

# JVM 옵션 필요하면 추가 가능
ENTRYPOINT ["java", "-jar", "app.jar"]
