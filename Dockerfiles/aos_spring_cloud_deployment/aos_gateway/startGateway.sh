#!/bin/bash
ash wait-for-it.sh "${POSTGRES_IP}" "${POSTGRES_PORT}"

java -jar gateway/gateway.jar
tail -f /dev/null
