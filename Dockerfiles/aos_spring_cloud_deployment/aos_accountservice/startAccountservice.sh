#!/bin/bash
cd accountservice
ash wait-for-it.sh "${CONFIG_SERVICE_URI}"
java -jar accountservice.jar -DCONFIG_FILE_URI=${CONFIG_FILE_URI} -DACTIVE_PROFILE=${ACTIVE_PROFILE}
tail -f /dev/null