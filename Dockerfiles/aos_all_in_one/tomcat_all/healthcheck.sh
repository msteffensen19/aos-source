#!/bin/sh

status=`curl http://${MAIN_IP}:${MAIN_PORT}/order/api/v1/healthcheck 2> /dev/null`
if [ "$status" != "\"SUCCESS\"" ];then 
  exit 1
fi
