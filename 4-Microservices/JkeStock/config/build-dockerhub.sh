#!/usr/bin/env bash

echo "build Started ...."
pwd
ls -l

cd ..


mvn clean package

docker build -f config/Dockerfile -t admjke/jke-stock:latest .
docker login -u admjke
docker push admjke/jke-stock:latest


echo "build completed ...."