FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp

ARG DEPENDENCY=build
COPY ${DEPENDENCY}/libs/* app.jar
EXPOSE 8500

ENTRYPOINT ["java", "-Dserver.port=8085", "-jar", "/app.jar"]
CMD ["--spring.profiles.active=local"]
