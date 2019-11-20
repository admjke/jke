#!/usr/bin/env bash

echo "build Started ...."
pwd
ls -l

cd ..

./gradlew compileJava

./gradlew build

docker build -f config/Dockerfile -t eucluster.icp:8500/jke-app/jke-batch:latest .

docker login -u admin -p ICPadmin01 eucluster.icp:8500

docker push eucluster.icp:8500/jke-app/jke-batch:latest

echo "build completed ...."