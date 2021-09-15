FROM openjdk:11
COPY "./target/springboot-actuator-0.0.1-SNAPSHOT.jar" "circuit-breaker.jar"
EXPOSE 8091
ENTRYPOINT [ "java", "-jar", "circuit-breaker.jar" ]
