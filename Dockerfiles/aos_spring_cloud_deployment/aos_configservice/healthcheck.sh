#!/bin/sh

status=`curl http://localhost:8080/actuator/health 2> /dev/null`
if [ "$status" == "\"UP\"" ];then
  exit 0
fi
