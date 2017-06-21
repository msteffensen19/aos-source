#!/bin/bash
. .env

# evaluate the workspace
workspace=`pwd`
one_level_up_workspace="${workspace%/*}"
two_levels_up_workspace="${one_level_up_workspace%/*}"
three_levels_up_workspace="${two_levels_up_workspace%/*}"
workspace=${three_levels_up_workspace}
three_levels_up_workspace=$(echo "$three_levels_up_workspace" | sed 's/\//\\\//g')
command1="sed -i 's/WORKSPACE/${three_levels_up_workspace}\/accountservice/g' docker-compose.yml"
eval $command1

#workspace=$(echo "$workspace" | sed 's/\//\\\//g')
#command2="sed -i 's/WORKSPACE_PATH_CALCULATED/${workspace}\//g' .env"
#eval $command2

sed -i "s/POSTGRES_PORT/${POSTGRES_PORT}/g" docker-compose.yml
sed -i "s/MAIN_PORT/${MAIN_PORT}/g" docker-compose.yml
sed -i "s/ACCOUNT_PORT/${ACCOUNT_PORT}/g" docker-compose.yml
sed -i "s/REGISTRY_PORT/${REGISTRY_PORT}/g" docker-compose.yml
sed -i "s/JENKINS_PORT/${JENKINS_PORT}/g" docker-compose.yml
sed -i "s/TAG/${TAG}/g" docker-compose.yml

if [ "$QUALI" == "NO" ];then
# change host name
 docker node ls | grep -v Leader | grep -v HOSTNAME | awk '{print $2}' | while read line; do command="sed -i '0,/HOST_NAME/{s/HOST_NAME/$line/}' docker-compose.yml"; echo $command; eval $command; done
# change public ip
 docker node ls | grep -v Leader | grep -v HOSTNAME | awk '{print $2}' | xargs docker inspect $1 | grep "Addr" | awk '{ gsub("\"",""); print $2}' | while read line; do command="sed -i '0,/PUBLIC_IP_CALCULATED/{s/PUBLIC_IP_CALCULATED/$line/}' .env"; echo $command; eval $command; done

# host name
 command3="sed -i 's/HOST_NAME/$(docker node ls | grep -w Leader | awk '{print $3}')/' docker-compose.yml"
 eval $command3
# ip of the host
 command4="sed -i 's/PUBLIC_IP_CALCULATED/$(docker node ls | grep -w Leader | docker inspect $(awk '{print $3}') | grep -m2 "Addr" | tail -n1 | awk '{ gsub("\"",""); print $2}' | awk -F":" '{print $1}')/' .env"
 eval $command4
 
 #edit .git/hooks
 echo \#\!/bin/bash$'\n'"curl -X POST http://${JENKINS_IP}:${JENKINS_PORT}/job/DEMOAPP-PIPLINE/build" > $workspace/.git/hooks/post-commit
 chmod +x $workspace/.git/hooks/post-commit

else
 #we assume the machins ip are already in .env
 #change HOST_NAME to the service type, for the next command
 list="POSTGRES MAIN ACCOUNT JENKINS JENKINS"
 for value in $list; do sed -i "0,/HOST_NAME/s/HOST_NAME/$value/" docker-compose.yml ; done

 docker node ls | grep -v Leader | grep -v HOSTNAME | awk '{print $2}' | while read line
 do
  public_ip=`docker inspect $line | grep "Addr" | awk '{ gsub("\"",""); print $2}'`
  case "$public_ip" in
   "$POSTGRES_IP") sed -i "s/POSTGRES/$line/" docker-compose.yml;;
   "$MAIN_IP") sed -i "s/MAIN/$line/" docker-compose.yml;;
   "$ACCOUNT_IP") sed -i "s/ACCOUNT/$line/" docker-compose.yml;;
  esac
 done
 
 command5="sed -i 's/JENKINS/$(docker node ls | grep -w Leader | awk '{print $3}')/' docker-compose.yml"
 eval $command5
 
 sed -i 's/.*WORKSPACE.*//g' docker-compose.yml
fi
. .env
ssh-keyscan ${ACCOUNT_IP} >> /root/.ssh/known_hosts
echo "{ \"insecure-registries\":[\"${REGISTRY_IP}:${REGISTRY_PORT}\"] }" | sshpass -p ${USER_PASSWORD} ssh root@"${ACCOUNT_IP}" "cat > /etc/docker/daemon.json"
sshpass -p ${USER_PASSWORD} ssh root@"${ACCOUNT_IP}" "service docker restart"

docker login -u=advantageonlineshoppingapp -p=W3lcome1
docker stack deploy --with-registry-auth -c docker-compose.yml STACK

command6="sed -i 's/performancetesting\/aos-accountservice.*/${REGISTRY_IP}:5000\/aos-accountservice/g' docker-compose.yml"
eval $command6