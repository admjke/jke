#!/usr/bin/env bash

cd ..

pwd
ls -l ./kube-resources/
rm -rfv ./kube-resources/*
ls -l ./kube-resources/

helm template jke-application --output-dir ./kube-resources/
helm template jke-prerequisite --output-dir ./kube-resources/