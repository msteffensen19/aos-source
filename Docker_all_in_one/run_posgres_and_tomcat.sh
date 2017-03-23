#!/bin/bash
usr/local/bin/docker-entrypoint.sh postgres &
echo "in sleep"
sleep 34
echo "start tomcat"
"/opt/apache-tomcat-8.5.12/bin/catalina.sh" run >> /opt/log.txt
