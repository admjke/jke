#!/usr/bin/env bash

echo "build Started ...."
pwd
ls -l

cd ..

mvn clean package

docker build -f config/Dockerfile -t eucluster.icp:8500/jke-app/jke-stock:latest .

docker login -u admin -p ICPadmin01 eucluster.icp:8500

docker push eucluster.icp:8500/jke-app/jke-stock:latest


echo "build completed ...."