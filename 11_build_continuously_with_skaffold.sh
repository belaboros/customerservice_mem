#!/bin/bash
set -e    # exit if any command fail


echo -e "\n\n\nStart building continuously with Skaffold"


#skaffold run -p dev
#skaffold dev --default-repo https://cloud.docker.com/repository/docker/belaboros/customerservicemem
#reflex -r "\.java$" -- bash -c 'mvn install && skaffold run'


DIR_TO_WATCH=src
BUILD_COMMAND="mvn clean install"
DEPLOY_COMMAND="skaffold run"


while true
do
    watch -d -t -g ls -lR ${DIR_TO_WATCH} && ${BUILD_COMMAND} && ${DEPLOY_COMMAND}
    sleep 1     # to allow break script by Ctrl+c
done

