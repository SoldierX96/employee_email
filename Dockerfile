# Etapa de compilación
FROM maven:3.8.5-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENV BD_HOST=localhost
ENV BD_USERNAME=sa
ENV BD_PASSWORD=12345678Ab
ENV BD_NAME=employee_bd
ENV EMAIL_TO=giancarlos.ra1710@gmail.com


CMD ["java", "-jar", "app.jar"]