#!/bin/bash
bash wait-for-it.sh "${POSTGRES_IP}" "${POSTGRES_PORT}"
java -jar accountservice/accountservice.jar -DCONFIG_FILE_URI=${CONFIG_FILE_URI} -DACTIVE_PROFILE=${ACTIVE_PROFILE}
tail -f /dev/null
