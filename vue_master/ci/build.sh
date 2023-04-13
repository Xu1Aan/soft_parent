#!/bin/bash

pwd
ls -la

cnpm i
npm run build

# echo 'docker build'
# docker build -t 172.17.0.57:5000/saas-hrm-vue:1.0.$CI_PIPELINE_ID .

# echo 'docker tag'
# docker tag 172.17.0.57:5000/saas-hrm-vue:1.0.$CI_PIPELINE_ID 172.17.0.57:5000/saas-hrm-vue:latest

# echo 'docker push'
# docker push 172.17.0.57:5000/saas-hrm-vue:latest

# echo 'docker rmi'
# docker rmi 172.17.0.57:5000/saas-hrm-vue:1.0.$CI_PIPELINE_ID
# docker rmi 172.17.0.57:5000/saas-hrm-vue:latest

# docker rmi -f $(docker images | grep saas-hrm-vue)
