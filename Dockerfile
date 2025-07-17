
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package

CMD ["java", "-jar", "target/gyaktest-0.0.1-SNAPSHOT.jar"]
