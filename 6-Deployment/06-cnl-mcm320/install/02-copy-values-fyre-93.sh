#!/usr/bin/env bash
echo 'copy started '

# set the value for this variable...
FOLDER_NAME_FROM=values-fyre-93

export FOLDER_NAME_FROM
sh ./09-copy-values-base.sh

echo 'copy completed'