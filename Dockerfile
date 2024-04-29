FROM 172.16.131.31/base/maven:3.6.1-jdk-8-alpine as builder

WORKDIR /app
ADD ./ /app

RUN mvn install

FROM 172.16.131.31/base/openjdk:8-jdk-alpine

# 设定时区
ENV TZ=Asia/Shanghai
RUN echo 'http://mirrors.aliyun.com/alpine/v3.11/main' > /etc/apk/repositories; \
    echo 'http://mirrors.aliyun.com/alpine/v3.11/community' >>/etc/apk/repositories; \
    set -eux; \
    apk add --no-cache --update fontconfig ttf-dejavu; \
    apk add --no-cache --update tzdata; \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime; \
    echo $TZ > /etc/timezone;

WORKDIR /app

COPY --from=builder /app/target/winner_hbServer-1.0.0-SNAPSHOT.jar /app/winner_hbServer-1.0.0-SNAPSHOT.jar
COPY --from=builder /app/startup/dev/application.properties /app/application.properties
COPY --from=builder /app/pay/dev/ /app/pay/

ENTRYPOINT ["sh", "-c", "java -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -jar -Dspring.config.location=application.properties winner_hbServer-1.0.0-SNAPSHOT.jar"]

EXPOSE 9093
