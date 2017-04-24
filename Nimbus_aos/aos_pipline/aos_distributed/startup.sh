#!/bin/bash
. .env

# evaluate the workspace
workspace=`pwd`
one_level_up_workspace="${workspace%/*}"
two_levels_up_workspace="${one_level_up_workspace%/*}"
newstring=""
IFS='/' read -r -a array <<< "${two_levels_up_workspace}"
for element in "${array[@]}"
do
    echo "$element"
    newstring="$newstring$element\/"
    echo "newstring=$newstring"
done
command1="sed -i 's/WORKSPACE/${newstring}accountservice/g' docker-compose.yml"
eval $command1

newstring2=""
IFS='/' read -r -a array2 <<< "${workspace}"
for element2 in "${array2[@]}"
do
    echo "$element2"
    newstring2="$newstring2$element2\/"
    echo "newstring2=$newstring2"
done
command2="sed -i 's/WORKSPACE_PATH_CALCULATED/${newstring2}/g' .env"
eval $command2


sed -i "s/POSTGRES_PORT/${POSTGRES_PORT}/g" docker-compose.yml
sed -i "s/MAIN_PORT/${MAIN_PORT}/g" docker-compose.yml
sed -i "s/ACCOUNT_PORT/${ACCOUNT_PORT}/g" docker-compose.yml
sed -i "s/JENKINS_PORT/${JENKINS_PORT}/g" docker-compose.yml

# change host name
 docker node ls | grep -v Leader | grep -v HOSTNAME | awk '{print $2}' | while read line; do command="sed -i '0,/HOST_NAME/{s/HOST_NAME/$line/}' docker-compose.yml"; echo $command; eval $command; done
# change public ip
 docker node ls | grep -v Leader | grep -v HOSTNAME | awk '{print $2}' | xargs docker inspect $1 | grep "Addr" | awk '{ gsub("\"",""); print $2}' | while read line; do command="sed -i '0,/PUBLIC_IP_CALCULATED/{s/PUBLIC_IP_CALCULATED/$line/}' .env"; echo $command; eval $command; done
#ip of the host
if [ "${PUBLIC_IP}" = "LOCAL" ]; then
 interface=`route | grep -w "default" | awk '{print $8}'`
 ip=`ifconfig | grep -A1 ${interface} | awk -F":" '/inet addr/ {print $2}' | awk '{print $1}'`
elif [ "${PUBLIC_IP}" = "AMAZON" ]; then
 ip=`curl http://169.254.169.254/latest/meta-data/public-ipv4`
else
 ip=${PUBLIC_IP}
fi
#public_ip=`curl http://169.254.169.254/latest/meta-data/public-ipv4`
command3="sed -i 's/PUBLIC_IP_CALCULATED/${ip}/g' .env"
eval $command3
#host name of the host
host_name=`docker node ls | grep -w Leader | awk '{print $3}'`
command4="sed -i 's/HOST_NAME/${host_name}/g' docker-compose.yml"
eval $command4

docker login -u=advantageonlineshoppingapp -p=W3lcome1
docker stack deploy --with-registry-auth -c docker-compose.yml STACK
