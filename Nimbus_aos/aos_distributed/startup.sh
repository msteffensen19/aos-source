#!/bin/bash
. .env

sed -i "s/POSTGRES_PORT/${POSTGRES_PORT}/g" docker-compose.yml
sed -i "s/MAIN_PORT/${MAIN_PORT}/g" docker-compose.yml
sed -i "s/ACCOUNT_PORT/${ACCOUNT_PORT}/g" docker-compose.yml

# change host name
 docker node ls | grep -v Leader | grep -v HOSTNAME | awk '{print $2}' | while read line; do command="sed -i '0,/HOST_NAME/{s/HOST_NAME/$line/}' docker-compose.yml"; echo $command; eval $command; done
# change public ip
 docker node ls | grep -v Leader | grep -v HOSTNAME | awk '{print $2}' | xargs docker inspect $1 | grep "Addr" | awk '{ gsub("\"",""); print $2}' | while read line; do command="sed -i '0,/PUBLIC_IP/{s/PUBLIC_IP/$line/}' .env"; echo $command; eval $command; done
#ip of the host
public_ip=`curl http://169.254.169.254/latest/meta-data/public-ipv4`
command4="sed -i 's/PUBLIC_IP/${public_ip}/g' .env"
eval $command4
#host name of the host
host_name=`docker node ls | grep -w Leader | awk '{print $3}'`
command5="sed -i 's/HOST_NAME/${host_name}/g' docker-compose.yml"
eval $command5

docker login -u=advantageonlineshoppingapp -p=W3lcome1
docker stack deploy --with-registry-auth -c docker-compose.yml STACK