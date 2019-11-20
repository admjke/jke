#!/usr/bin/env bash
echo 'copy values base started '

# FOLDER_NAME_FROM value (ex: values-fyre-93) would come from the previous script
echo "FOLDER_NAME_FROM"
echo $FOLDER_NAME_FROM

# cat ../values/$FOLDER_NAME_FROM/values-mcm.yaml

cat ../values/$FOLDER_NAME_FROM/values.yaml > ../jke-application/values.yaml
cat ../values/$FOLDER_NAME_FROM/values.yaml > ../jke-prerequisite/values.yaml

echo 'copy values base completed'
