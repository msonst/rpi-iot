FROM gradle:jdk10 as builder

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:10-jre-slim
EXPOSE 8080
COPY --from=builder /home/gradle/src/rpi-iot-server.web/build/distributions/rpi-iot-server.web.tar /app/
WORKDIR /app
RUN tar -xvf rpi-iot-server.web.tar
WORKDIR /app/rpi-iot-server.web
CMD bin/rpi-iot-server.web