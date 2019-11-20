#!/usr/bin/env bash

echo "build Started ...."
pwd
ls -l

cd ..

mvn clean package

docker build -f config/Dockerfile -t admjke/jke-interest:latest .
docker login -u admjke
docker push admjke/jke-interest:latest


echo "build completed ...."


