FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy parent and service POMs first to leverage Docker layer caching
COPY pom.xml .
COPY service/pom.xml service/

# Pre-fetch dependencies (cached in /root/.m2)
RUN --mount=type=cache,target=/root/.m2 \
    mvn dependency:go-offline -B -pl service -am || true

# Copy the full source tree and build the service module
COPY . .
RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package -DskipTests -B -pl service -am

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/service/target/*.jar app.jar
EXPOSE 9080
ENTRYPOINT ["java", "-jar", "app.jar"]

