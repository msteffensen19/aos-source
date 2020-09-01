MAINTAINER Assaf Kubany
FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-alpine-slim


ENV http_proxy="http://proxy.il.hpecorp.net:8080"
ENV https_proxy="http://proxy.il.hpecorp.net:8080"

RUN apt-get update && \
    apt-get install -y \
    openjdk-8-jdk \
    postgresql \
    vim

RUN mkdir /accountservice
COPY accountservice.jar /accountservice
COPY wait-for-it.sh /accountservice
COPY startAccountservice.sh /accountservice
COPY healthcheck.sh /accountservice
COPY healthcheck.xml /accountservice
RUN chmod +x /healthcheck.sh
RUN chmod +x /startAccountservice.sh


ENTRYPOINT ["sh", "-c", "/startAccountservice.sh"]
HEALTHCHECK --interval=60s --timeout=30s --retries=5 \
CMD /healthcheck.sh
