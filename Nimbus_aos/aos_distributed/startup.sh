#!/bin/bash
ip=`curl http://169.254.169.254/latest/meta-data/public-ipv4`
command1="sed -i 's/HOST_IP_CALCULATED/$ip/g' .env"
eval $command1
docker-compose up --pull -d

