FROM tomcat:9.0.2-jre8
MAINTAINER Michal Edery

ENV http_proxy="http://proxy.il.hpecorp.net:8080"
ENV https_proxy="http://proxy.il.hpecorp.net:8080"

RUN apt-get update && \
    apt-get install -y \
    openjdk-8-jdk \
    postgresql \
    vim

RUN rm -rf webapps/ROOT
COPY accountservice.war webapps
COPY updatewars.sh webapps
COPY wait-for-it.sh webapps
COPY startTomcat.sh /
RUN chmod +x /startTomcat.sh

RUN sed -i -e '$i <role rolename="manager-gui"\/>\n<role rolename="manager-script"\/>\n<user username="tomcatadmin" password="Password1" roles="manager-gui,manager-script"\/>' /usr/local/tomcat/conf/tomcat-users.xml && \
    cat /usr/local/tomcat/conf/tomcat-users.xml
	
ENV http_proxy=""
ENV https_proxy=""
	
ENTRYPOINT ["sh", "-c", "/startTomcat.sh"]

