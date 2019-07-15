kubectl apply -f customerservicemem-deployment.yml

kubectl get all

kubectl describe service customerservicemem



echo -e "\n\n\nQuery CustomerServiceMem  service:"
wget -qO- $(kubectl get services | grep customerservicemem | tr -s ' ' | cut -d ' ' -f 3,5 --output-delimiter ':' | cut -d '/' -f 1)/api/customers