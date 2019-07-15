#!/bin/bash
set -e


echo -e "\n\n\n1/3 Pushing Docker image to GitHub"
docker push belaboros/customerservicemem


echo -e "\n\n\n2/3 Deploying to Kubernetes"
kubectl apply -f k8s/customerservicemem-deployment.yml



echo -e "\n\n\nK8S resources:"
kubectl get all



echo -e "\n\n\n3/3 Testing the customerservicemem service in Kuberenetes"
wget -qO- $(kubectl get services | grep customerservicemem | tr -s ' ' | cut -d ' ' -f 3,5 --output-delimiter ':' | cut -d '/' -f 1)/api/customers







