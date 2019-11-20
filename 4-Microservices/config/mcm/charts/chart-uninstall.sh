#!/usr/bin/env bash
echo 'uninstallation started '

###### NOTE - You might have executed the below command before you call this sh...
###   cloudctl login -a https://9.204.168.81:8443 --skip-ssl-validation
###   Here the given IP is the IP of your ICP Cluster

###### install
helm del --purge helm-jke-1-common --tls
helm del --purge helm-jke-2-web --tls
helm del --purge helm-jke-3-mq --tls
helm del --purge helm-jke-4-api --tls
helm del --purge helm-jke-5-bankstreet --tls

echo 'uninstallation completed'