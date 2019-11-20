# Installing JKE application

Here are the steps for installing JKE application in MCM cluster or in a single cluster.

We are going to install 4 services.

* jke-web
* jke-batch
* jke-interest
* jke-stock.

The first 2 services would be in MCM cluster.

The other 2 services will be in different public clouds.

Note: You need to download the deployment files from the folder 6-Deployment/cnl of this repository to proceed further in this installation.

## Pre - Installation steps

1. Make sure the the below container images are available in the MCM hub registry. Those images could have been pushed through Jenkins CICD pipeline from the source available in GIT.

```
mycluster.icp:8500/jke-app/jke-web
mycluster.icp:8500/jke-app/jke-batch

```

Note: Scope of the images should be in global, not in the namespace. You may need to change it through portal.


2. Make sure the the below container images are available in any image registry. Those images could have been pushed through Jenkins CICD pipeline from the source available in GIT.

```
mycluster.icp:8500/jke-app/jke-interest
mycluster.icp:8500/jke-app/jke-stock

```

3. Use the below command to create docker registry credentials. Use the appropriate docker-server, username and password.

```
kubectl create secret docker-registry jke-docker-registry-secret --docker-server=mycluster.icp:8500 --docker-username=admin --docker-password=admin --docker-email=null --namespace jke-app  --dry-run -o yaml | tee credentials.yaml
```

4. Modify the required values in the property files avalable in the below folder. 

```
/values/values-cnl
```

You may need to modify the below list of properties.

* Image Repository
* Image Pull secrete
* DB2 properties
* MQ Properties
* URLs (interest and stock)

5. Replace the above created jke-docker-registry-secret value in the dockerImagePullSecretValue property of the above property files

6. Run the below script to copy the modified values to the required charts

```
sh 02-copy-values-cnl.sh
```

7. Run the below script to lint the charts and see the scripts are ok.

```
sh 11-lint.sh
```

8. Run the below script to generate plain kubernetes resources yaml files. 

```
sh 21-Kube-resources-Generate.sh
```

This will create those files under the folder kube-resources.

## Installation steps for deploying in MCM cluster

1. Create the below namespace in MCM Hub

```
Name : jke-app
PSP : ibm-privileged-psp
```

2. Run the below script to login into the MCM Hub. 

Before running, make sure the IP, user and password are correct in the file.

```
sh 32-clouldctl-login-cnl.sh
```

3. Run the below script in MCM Hub

```
sh 41-mcm-install.sh
```

## Installation steps for deploying jke-interest in OCP/IKS/ICP

1. Set the Kubectl CLI context to the appropriate the environment


2. Create the below namespace

```
Name : jke-app
```

3. Run the bleow command to create the kubernetes resources (to deploy the jke-interest)

```
sh 71-Kube-Install-jke-interest.sh
```

## Installation steps for deploying jke-stock in OCP/IKS/ICP

1. Set the Kubectl CLI context to the appropriate the environment

2. Create the below namespace

```
Name : jke-app
```

3. Run the bleow command to create the kubernetes resources (to deploy the jke-stock)

```
sh 81-Kube-Install-jke-stock.sh
```
