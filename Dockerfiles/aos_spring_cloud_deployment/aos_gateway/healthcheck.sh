#!/bin/sh

status=`curl http://localhost:$${GATEWAY_PORT}/actuator/health 2> /dev/null`
if [ "$status" == "\"UP\"" ];then
  exit 0
fi
