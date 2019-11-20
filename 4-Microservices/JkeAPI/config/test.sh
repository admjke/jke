#!/usr/bin/env bash
while sleep 0.1;
  do
    curl "$1"
    echo "";
  done ;