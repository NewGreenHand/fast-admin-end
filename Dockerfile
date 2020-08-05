FROM java:8-jre
MAINTAINER kenfei <kenfei@aliyun.com>

ADD ./app/target/admin-exec.jar /app/

EXPOSE 8090

CMD ["java", "-Xmx200m", "-jar", "/app/admin-exec.jar"]
