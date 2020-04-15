FROM openjdk:8-jre-alpine
ARG JAR_FILE=target/*.jar
RUN echo ${JAR_FILE}
COPY ${JAR_FILE} app.jar
ENTRYPOINT java ${JAVA_OPTIONS} -jar /app.jar
