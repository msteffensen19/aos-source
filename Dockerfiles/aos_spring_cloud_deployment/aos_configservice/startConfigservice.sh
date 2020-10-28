#!/bin/bash
#ash wait-for-it.sh "${POSTGRES_IP}" "${POSTGRES_PORT}"
if [ ! -f /initialized.txt ]; then # Run initialization logic
    sed -i "s/ACCOUNT_SERVICE_NAME/${ACCOUNT_SERVICE_NAME}/g" configservice/microservices/application-${ACTIVE_PROFILE}.yml
    sed -i "s/POSTGRES_SERVICE_NAME/${POSTGRES_SERVICE_NAME}/g" configservice/microservices/application-${ACTIVE_PROFILE}.yml
    sed -i "s/MAIN_SERVICE_NAME/${MAIN_SERVICE_NAME}/g" configservice/microservices/application-${ACTIVE_PROFILE}.yml
    sed -i "s/MAIN_SERVICE_NAME/${MAIN_SERVICE_NAME}/g" configservice/microservices/aos-gateway-${ACTIVE_PROFILE}.yml
    sed -i "s/ACCOUNT_SERVICE_NAME/${ACCOUNT_SERVICE_NAME}/g" configservice/microservices/aos-gateway-${ACTIVE_PROFILE}.yml
    touch /initialized.txt
fi
java -jar configservice/configservice.jar -DCONFIG_FILE_URI=${CONFIG_FILE_URI}
tail -f /dev/null
