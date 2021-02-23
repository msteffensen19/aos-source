#!/bin/bash
cd gateway
ash wait-for-it.sh "${CONFIG_SERVICE_URI}"
java -Dhttp.proxyHost="${JAVA_PROXY_HOST}" -Dhttp.proxyPort="${JAVA_PROXY_PORT}" -Dhttp.nonProxyHosts="${JAVA_NO_PROXY}" -jar gateway.jar
tail -f /dev/null
