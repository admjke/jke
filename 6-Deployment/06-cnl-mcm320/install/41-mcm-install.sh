#!/usr/bin/env bash
echo 'installation started '

helm install --name jke-application ../jke-application --tls

echo 'installation completed'

