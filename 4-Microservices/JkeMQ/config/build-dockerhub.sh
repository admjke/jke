#!/usr/bin/env bash

echo "build Started ...."
pwd
ls -l

cd ..

./gradlew compileJava

./gradlew build

docker build -f config/Dockerfile -t admjke/jke-batch:latest .
docker login -u admjke
docker push admjke/jke-batch:latest


echo "build completed ...."


