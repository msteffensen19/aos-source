#!/bin/bash
container_id=`docker ps -a | grep "performancetesting/aos-${1}-dev" | awk '{print $1}'`
docker cp /root/HPEAppPulseJava_1.60_AdvantageOnlineShopping.zip ${container_id}:/usr/local/tomcat
docker exec -it ${container_id} bash -c './bin/shutdown.sh; unzip HPEAppPulseJava_1.60_AdvantageOnlineShopping.zip; AppPulseJavaAgent/lib; java -jar AppPulseAgent.jar setup; ./bin/startup.sh'