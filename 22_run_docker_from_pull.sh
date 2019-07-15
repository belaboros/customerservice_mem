#!/bin/sh

DOCKER_IMAGE=belaboros/customerservicemem

docker pull ${DOCKER_IMAGE}
docker run -p 9090:9090 ${DOCKER_IMAGE}




