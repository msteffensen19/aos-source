#!/bin/sh

status=`curl http://localhost:8080/order/api/v1/healthcheck 2> /dev/null`
if [ "$status" == "\"SUCCESS\"" ];then
  exit 0
fi
