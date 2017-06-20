#!/bin/bash
cd webapps
bash updatewars.sh
./wait-for-it.sh "${POSTGRES_IP}"
catalina.sh run
tail -f /dev/null
