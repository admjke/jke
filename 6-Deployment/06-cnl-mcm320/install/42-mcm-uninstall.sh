#!/usr/bin/env bash
echo 'un-installation started '

helm del --purge jke-application --tls

echo 'un-installation completed'
