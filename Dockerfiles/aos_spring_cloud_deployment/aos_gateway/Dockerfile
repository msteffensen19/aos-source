FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-alpine-slim
MAINTAINER Assaf Kubany

ENV http_proxy="http://proxy.il.hpecorp.net:8080"
ENV https_proxy="http://proxy.il.hpecorp.net:8080"

RUN apk  update && \
    apk  add \
    curl \
    vim

RUN mkdir /gateway
COPY wait-for-it.sh /gateway
COPY gateway.jar /gateway
COPY startGateway.sh /gateway
COPY healthcheck.sh /gateway
RUN chmod +x /gateway/healthcheck.sh
RUN chmod +x /gateway/startGateway.sh

ENV http_proxy=""
ENV https_proxy=""

ENTRYPOINT ["sh","/gateway/startGateway.sh"]
HEALTHCHECK --interval=60s --timeout=30s --retries=5 \
CMD /gateway/healthcheck.sh
