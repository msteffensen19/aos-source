#!/bin/bash
set -e

config_service_uri="$1"
shift
echo "starting"
cmd="$@"
status=status=$(curl -s "$config_service_uri"/actuator/health)
while [[ ! $status = *"UP"*  ]]
    echo "Waiting for config service"
    status=$(curl -s "$config_service_uri"/actuator/health)
    echo $status
    do true; done

>&2 echo "config service is up - executing command"
exec $cmd
