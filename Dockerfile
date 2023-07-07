FROM adoptopenjdk:11-jdk-hotspot AS build
WORKDIR /app
RUN apt-get update && apt-get install -y maven
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=build /app/target/beach-product-rental-0.0.1.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
