FROM openjdk:11
COPY "./target/springboot-actuator-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 8091
ENTRYPOINT [ "java", "-jar", "app.jar" ]
