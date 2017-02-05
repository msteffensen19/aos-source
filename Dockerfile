FROM tomcat:8.0-jre8
MAINTAINER Michal Edery

RUN rm -rf webapps/ROOT
COPY root/target/wars.zip webapps
RUN cd webapps && unzip wars.zip
