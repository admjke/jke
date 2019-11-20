#!/usr/bin/env bash

echo "build Started ...."
pwd
ls -l

cd ..

./gradlew compileJava

./gradlew build

docker build -f config/Dockerfile -t admjke/jke-web:latest .
docker login -u admjke
docker push admjke/jke-web:latest


echo "build completed ...."


