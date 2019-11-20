#!/usr/bin/env bash
echo 'installation started '

helm install --name helm-jke-1-common ./chart-jke-1-common --tls
helm install --name helm-jke-2-web ./chart-jke-2-web --tls
helm install --name helm-jke-3-mq ./chart-jke-3-mq --tls
helm install --name helm-jke-4-api ./chart-jke-4-api --tls
helm install --name helm-jke-5-bankstreet ./chart-jke-5-bankstreet --tls

echo 'installation completed'