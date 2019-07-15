#!/bin/bash
set -e    # exit if any command fail


echo -e "\n\n\n1/2 Building JAR"
mvn clean package

DOCKER_IMAGE_NAME="belaboros/customerservicemem"

echo -e "\n\n\n2/2 Building docker image: ${DOCKER_IMAGE_NAME}"
docker build . -t ${DOCKER_IMAGE_NAME}



