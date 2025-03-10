FROM bellsoft/liberica-openjdk-alpine:21 AS builder

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM bellsoft/liberica-openjdk-alpine:21
COPY --from=builder build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]