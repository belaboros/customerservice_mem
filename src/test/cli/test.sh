#!/bin/bash

set -e


SERVICE_ROOT=localhost:9090
#SERVICE_ROOT=10.105.149.216:9090


echo -e "\n\n\n1. HTTP GET info"
curl ${SERVICE_ROOT}/info | jq


echo -e "\n\n\n2. HTTP POST customers/samples"
curl --silent -X POST ${SERVICE_ROOT}/api/customers/samples | jq


echo -e "\n\n\n3. HTTP GET customers"
curl --silent ${SERVICE_ROOT}/api/customers | jq


echo -e "\n\n\n4. HTTP POST customers?title=Mr&name=Anderson"
JSON_MR_ANDERSON=$(curl --silent -d "title=Mr&name=Anderson" -X POST ${SERVICE_ROOT}/api/customers)
echo ${JSON_MR_ANDERSON} | jq
ID_MR_ANDERSON=$(echo ${JSON_MR_ANDERSON} | jq -r '.id')
echo "ID of Mr Anderson: ${ID_MR_ANDERSON}"


echo -e "\n\n\n5. HTTP GET customers"
curl --silent ${SERVICE_ROOT}/api/customers | jq


echo -e "\n\n\n6. HTTP GET customers/${ID_MR_ANDERSON}"
curl --silent -X GET ${SERVICE_ROOT}/api/customers/${ID_MR_ANDERSON} | jq


echo -e "\n\n\n6. HTTP PUT customers/${ID_MR_ANDERSON} {\"title\": \"Mr\", \"name\":\"Neo\"} "
#curl -X PUT -d "title=Mr&name=Neo"  ${SERVICE_ROOT}/api/customers/${ID_MR_ANDERSON} | jq
curl -X PUT -H "Content-Type: application/json" -d "{\"title\": \"Mr\", \"name\":\"Neo\"}"  ${SERVICE_ROOT}/api/customers/${ID_MR_ANDERSON} | jq





echo -e "\n\n\n5. HTTP GET customers"
curl --silent ${SERVICE_ROOT}/api/customers | jq



echo -e "\n\n\n7. HTTP DELETE customers/${ID_MR_ANDERSON}"
curl --silent -X DELETE ${SERVICE_ROOT}/api/customers/${ID_MR_ANDERSON} | jq


echo -e "\n\n\n8. HTTP GET customers"
curl --silent ${SERVICE_ROOT}/api/customers | jq


echo -e "\n\n\n9. HTTP DELETE customers/all"
curl --silent -X DELETE ${SERVICE_ROOT}/api/customers/all | jq


echo -e "\n\n\n10. HTTP GET customers"
curl --silent ${SERVICE_ROOT}/api/customers | jq




