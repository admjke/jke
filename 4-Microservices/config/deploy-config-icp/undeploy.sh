#!/usr/bin/env bash

echo 'undeploy started'

kubectl delete -f secret-z-registry.yaml
kubectl delete -f role-binding.yaml
kubectl delete -f cluster-role.yaml

kubectl delete -f secret.yaml
kubectl delete -f configmap.yaml

kubectl delete -f service-web.yaml
kubectl delete -f service-mq.yaml
kubectl delete -f service-api.yaml
kubectl delete -f service-bankstreet.yaml

kubectl delete -f deployment-web.yaml
kubectl delete -f deployment-mq.yaml
kubectl delete -f deployment-api.yaml
kubectl delete -f deployment-bankstreet.yaml

echo 'un sdeploy completed'

