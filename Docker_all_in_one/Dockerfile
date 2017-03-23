FROM postgres:9.4.11
MAINTAINER Michal Edery

#set username and password
ENV POSTGRES_USER postgres

ENV POSTGRES_PASSWORD admin

# create databases and functions
ADD init_scripts /docker-entrypoint-initdb.d/

ENV http_proxy="http://web-proxy.il.hpecorp.net:8080"
ENV https_proxy="http://web-proxy.il.hpecorp.net:8080"

ENV JAVA_HOME /usr/lib/jvm/java-8-oracle
ENV PATH $JAVA_HOME/bin:$PATH

COPY run_posgres_and_tomcat.sh /
COPY apache-tomcat-8.5.12.tar.gz /opt
RUN cd / && chmod 777 run_posgres_and_tomcat.sh
RUN apt-get update && apt-get -y install software-properties-common && add-apt-repository "deb http://ppa.launchpad.net/webupd8team/java/ubuntu xenial main" && apt-get update && apt-get -y install wget && apt-get -y install curl && apt-get install unzip && echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && echo debconf shared/accepted-oracle-license-v1-1 seen true | debconf-set-selections && apt-get -y install oracle-java8-installer

EXPOSE 8080
EXPOSE 8009

RUN cd /opt && tar -xvf apache-tomcat-8.5.12.tar.gz && rm *.tar.gz && chown -R postgres:postgres /opt && chmod -R 777 /opt && cd apache-tomcat-8.5.12/webapps && rm -rf ROOT
COPY updatewars.sh "/opt/apache-tomcat-8.5.12/webapps"
COPY root/target/wars.zip /opt/apache-tomcat-8.5.12/webapps
RUN cd /opt/apache-tomcat-8.5.12/webapps && bash updatewars.sh

USER postgres
ENTRYPOINT ["/run_posgres_and_tomcat.sh"]
