#!/usr/bin/env bash

cd ..

kubectl create -f ./kube-resources/jke-prerequisite/templates/ --validate=false