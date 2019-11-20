#!/usr/bin/env bash

echo 'deploy started'

kubectl apply -f secret.yaml

kubectl apply -f service-web.yaml
kubectl apply -f service-mq.yaml
kubectl apply -f service-api.yaml
kubectl apply -f service-bankstreet.yaml

kubectl apply -f deployment-web.yaml
kubectl apply -f deployment-mq.yaml
kubectl apply -f deployment-api.yaml
kubectl apply -f deployment-bankstreet.yaml

echo 'deploy completed'

