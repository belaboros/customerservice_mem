#!/bin/bash


echo -e "\n\n\n1/5 Clean local builds"
mvn clean


DOCKER_NAME="belaboros/customerservicemem"


echo -e "\n\n\n2/5 Stop local Docker containers: ${DOCKER_NAME}"
docker ps -a --no-trunc  | grep ${DOCKER_NAME} | awk "{print $1}" | xargs -r --no-run-if-empty docker stop


echo -e "\n\n\n3/5 Delete local Docker containers: ${DOCKER_NAME}"
docker ps -a --no-trunc  | grep ${DOCKER_NAME} | awk "{print $1}" | xargs -r --no-run-if-empty docker rm


echo -e "\n\n\n4/5 Delete local Docker images: ${DOCKER_NAME}"
docker images --no-trunc | grep ${DOCKER_NAME} | awk "{print $3}" | xargs -r --no-run-if-empty docker rmi


echo -e "\n\n\n5/5 Delete Kubernetes deployment: ${DOCKER_NAME}"
kubectl delete -f k8s/customerservicemem-deployment.yml --ignore-not-found=true


