#!/bin/sh

status1=`curl http://${MAIN_IP}:${MAIN_PORT}/order/api/v1/healthcheck 2> /dev/null`
if [ "$x" != "\"SUCCESS\"" ];then 
  exit 1
fi
