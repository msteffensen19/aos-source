#!/bin/bash

config_service_uri="$1"
shift
echo "starting"
cmd="$@"
status=$(curl -s "$config_service_uri"/actuator/health)
x=0
while [[ "$x" == 0 ]]
    do
    echo "Waiting for config service"
    status=$(curl -s "$config_service_uri"/actuator/health)
    echo $status
    case "$status" in *UP*)
    x=1
     ;; esac
     sleep 2m
    done

>&2 echo "config service is up - executing command"
exec $cmd
