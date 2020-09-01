FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-alpine-slim
MAINTAINER Assaf Kubany

ENV http_proxy="http://proxy.il.hpecorp.net:8080"
ENV https_proxy="http://proxy.il.hpecorp.net:8080"

RUN apt-get update && \
    apt-get install -y \
    openjdk-8-jdk \
    postgresql \
    vim

RUN mkdir /configservice
COPY configservice.jar /configservice
COPY startConfigservice.sh /configservice
COPY healthcheck.sh /configservice
RUN chmod +x /healthcheck.sh
RUN chmod +x /startConfigservice.sh


ENTRYPOINT ["sh", "-c", "/startConfigservice.sh"]
HEALTHCHECK --interval=60s --timeout=30s --retries=5 \
CMD /healthcheck.sh
