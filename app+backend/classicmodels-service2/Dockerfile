FROM openjdk:16
WORKDIR /classicmodels-service2
ADD target/classicmodels-service2-0.0.1-SNAPSHOT.jar classicmodels-service2-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","classicmodels-service2-0.0.1-SNAPSHOT.jar"]

#FROM maven:3.8.2-jdk-11
#WORKDIR /classicmodels-service2
#COPY . .
#CMD mvn spring-boot:run