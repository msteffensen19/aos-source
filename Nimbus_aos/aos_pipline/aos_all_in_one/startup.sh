#!/bin/bash
. .env
if [ "${PUBLIC_IP}" = "LOCAL" ]; then
 interface=`route | grep -w "default" | awk '{print $8}'`
 ip=`ifconfig | grep -A1 ${interface} | awk -F":" '/inet addr/ {print $2}' | awk '{print $1}'`
elif [ "${PUBLIC_IP}" = "AMAZON" ]; then
 ip=`curl http://169.254.169.254/latest/meta-data/public-ipv4`
else
 ip=${PUBLIC_IP}
fi
command1="sed -i 's/HOST_IP_CALCULATED/$ip/g' .env"
eval $command1
. .env
workspace=`pwd`
one_level_up_workspace="${workspace%/*}"
two_levels_up_workspace="${one_level_up_workspace%/*}"
three_levels_up_workspace="${two_levels_up_workspace%/*}"
workspace=${three_levels_up_workspace}
three_levels_up_workspace=$(echo "$three_levels_up_workspace" | sed 's/\//\\\//g')
command2="sed -i 's/WORKSPACE/${three_levels_up_workspace}\/accountservice/g' docker-compose.yml"
eval $command2

echo echo \#\!/bin/bash$'\n'"curl -X POST http://${JENKINS_IP}:${JENKINS_PORT}/job/DEMOAPP-PIPLINE/build" > $workspace/.git/hooks/post-commit

docker login -u=advantageonlineshoppingapp -p=W3lcome1
docker-compose up -d