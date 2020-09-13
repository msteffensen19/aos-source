#!/bin/bash
cd gateway
ash wait-for-it.sh "${CONFIG_SERVICE_URI}"
java -jar gateway.jar
tail -f /dev/null
