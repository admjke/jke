#!/usr/bin/env bash

cd ..

helm lint jke-application

helm lint jke-prerequisite
