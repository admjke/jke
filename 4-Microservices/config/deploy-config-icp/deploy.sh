#!/usr/bin/env bash
echo 'deploy started 1'

echo deploy1 db2_host : $DB2_HOST
echo deploy db2_port : $DB2_PORT
echo deploy db2_password : $DB2_PASSWORD
echo deploy mq_host : $MQ_HOST
echo deploy mq_port : $MQ_PORT
echo deploy mq_password : $MQ_PASSWORD

##### Copy the existing files to temp folder
mkdir temp 
rm temp/*.* 
cp *.* temp
cd temp
chmod 777 *.*

###### Substitute variables in the temp folder
sed "s/{{ .Values.db2HostName }}/${DB2_HOST}/" secret.yaml > temp1.yaml
sed "s/{{ .Values.db2Port }}/${DB2_PORT}/" temp1.yaml > temp2.yaml
sed "s/{{ .Values.db2Password }}/${DB2_PASSWORD}/" temp2.yaml > temp1.yaml
sed "s/{{ .Values.mqHostName }}/${MQ_HOST}/" temp1.yaml > temp2.yaml
sed "s/{{ .Values.mqPort }}/${MQ_PORT}/" temp2.yaml > temp1.yaml
sed "s/{{ .Values.mqPassword }}/${MQ_PASSWORD}/" temp1.yaml > secret.yaml

###### Deplyoment 
kubectl apply -f secret-z-registry.yaml
kubectl patch serviceaccount hcl-jenkins-ibm-jenkins -p '{"imagePullSecrets": [{"name": "jke-docker-registry-secret"}]}'  --namespace hclapp  
kubectl apply -f cluster-role.yaml
kubectl apply -f role-binding.yaml

kubectl apply -f secret.yaml
kubectl apply -f configmap.yaml

kubectl apply -f service-web.yaml
kubectl apply -f service-mq.yaml
kubectl apply -f service-api.yaml
kubectl apply -f service-bankstreet.yaml

kubectl apply -f deployment-web.yaml
kubectl apply -f deployment-mq.yaml
kubectl apply -f deployment-api.yaml
kubectl apply -f deployment-bankstreet.yaml

### To delete the temp folders
# rm *.* 

echo 'deploy completed'