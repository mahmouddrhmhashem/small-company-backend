FROM openjdk:17
ARG JAR_FILE=target/*.jar
EXPOSE 8080
ADD target/realapp.jar realapp.jar
ENTRYPOINT ["java","-jar","/realapp.jar"]