FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY . /app

RUN ls && pwd

EXPOSE 8081

ENTRYPOINT [ "java","-jar","/app/build/libs/Ecomarket-0.0.1-SNAPSHOT.jar" ]