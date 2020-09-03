#!/bin/bash
bash wait-for-it.sh "${POSTGRES_IP}" "${POSTGRES_PORT}"

java -jar configservice/configservice.jar -DCONFIG_FILE_URI=${CONFIG_FILE_URI}
tail -f /dev/null
