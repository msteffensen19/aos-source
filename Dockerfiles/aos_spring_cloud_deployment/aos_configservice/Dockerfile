FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-alpine-slim
MAINTAINER Assaf Kubany

ENV http_proxy="http://proxy.il.hpecorp.net:8080"
ENV https_proxy="http://proxy.il.hpecorp.net:8080"

RUN apk  update && \
    apk  add \
    openjdk8 \
    postgresql \
    vim

RUN mkdir /configservice
COPY configservice.jar /configservice
COPY startConfigservice.sh /configservice
COPY healthcheck.sh /configservice
COPY microservices /configservice/microservices
RUN chmod +x /configservice/healthcheck.sh
RUN chmod +x /configservice/startConfigservice.sh

ENV http_proxy=""
ENV https_proxy=""

ENTRYPOINT ["sh","/configservice/startConfigservice.sh"]
HEALTHCHECK --interval=60s --timeout=30s --retries=5 \
CMD /configservice/healthcheck.sh
