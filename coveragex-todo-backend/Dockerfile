# -------------------------
# Build Stage (Maven)
# -------------------------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy Maven files
COPY pom.xml .
COPY src ./src

# Build JAR
RUN mvn -B clean package -DskipTests
#RUN mvn -B clean package

# -------------------------
# Run Stage (Final Image)
# -------------------------
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/*.jar coveragex.jar


EXPOSE 8080
ENTRYPOINT ["java", "-jar", "coveragex.jar"]


