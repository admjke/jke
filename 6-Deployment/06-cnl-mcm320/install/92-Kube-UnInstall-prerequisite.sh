#!/usr/bin/env bash

cd ..

kubectl delete -f ./kube-resources/jke-application/templates/ --validate=false