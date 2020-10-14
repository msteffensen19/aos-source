FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-alpine-slim
MAINTAINER Assaf Kubany



ENV http_proxy="http://proxy.il.hpecorp.net:8080"
ENV https_proxy="http://proxy.il.hpecorp.net:8080"

RUN apk  update && \
    apk  add \
    curl \
    vim

RUN mkdir /accountservice
COPY accountservice.jar /accountservice
COPY wait-for-it.sh /accountservice
COPY startAccountservice.sh /accountservice
COPY healthcheck.sh /accountservice
COPY healthcheck.xml /accountservice
RUN chmod +x /accountservice/healthcheck.sh
RUN chmod +x /accountservice/startAccountservice.sh

ENV http_proxy=""
ENV https_proxy=""

ENTRYPOINT ["sh", "/accountservice/startAccountservice.sh"]
HEALTHCHECK --interval=60s --timeout=30s --retries=5 \
CMD /accountservice/healthcheck.sh