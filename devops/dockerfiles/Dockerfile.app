# ========== Base Stage ==========
FROM eclipse-temurin:17-jdk as builder
WORKDIR /app

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package -DskipTests

# ========== Runtime Stage ==========
FROM eclipse-temurin:17-jre
LABEL maintainer="javakishore-veleti"

WORKDIR /app
COPY --from=builder /app/target/forex-events-kafka-oauth-impl-1.0.0.jar app.jar

EXPOSE 8080
ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
