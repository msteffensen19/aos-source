#!/bin/sh

status=`curl http://${MAIN_IP}:${MAIN_PORT}/order/api/v1/healthcheck 2> /dev/null`
while true
do
 if [ "$status" == "\"SUCCESS\"" ];then
  status=`curl http://localhost:8080/order/api/v1/healthcheck 2> /dev/null`
 else
  break
 fi
done
