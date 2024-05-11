FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY . /app

EXPOSE 8081

CMD ls

ENTRYPOINT [ "java","-jar","/app/build/libs/Ecomarket-0.0.1-SNAPSHOT.jar" ]