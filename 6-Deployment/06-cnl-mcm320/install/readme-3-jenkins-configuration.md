# Configuring the Jenkins CICD pipeline

The JKE applications CICD pipelines setup is explained here.

The below 4 services/apps are available in JKE, so we need to create 4 docker images using these pipeline.

* jke-web
* jke-batch
* jke-interest
* jke-stock

## Jenkins setup

1. Jenkins should have been installed in VM (not the container version)

2. The below softwares should be available in VM

* git
* docker
* maven

3. All the above softwares should be configured in the jenkins settings


## Scenario 1 : MCM hub is on ICP and MCM Clusters are also in ICP.

Lets assume that we have the following environment

* 1 MCM Hub
* 3 MCM clusters

Key  | Values
------------- | -------------
MCM hub IP Address  | 9.20.194.93
MCM hub docker registry | hubcluster.icp:8500

Using the CICD pipelines we have to create the below images and push it to the MCM hub private docker registry.

Key |
------------- | 
hubcluster.icp:8500/jke-app/jke-web |
hubcluster.icp:8500/jke-app/jke-batch |
hubcluster.icp:8500/jke-app/jke-interest |
hubcluster.icp:8500/jke-app/jke-stock |


Do the below steps in **jenkins** to setup the CICD pipeline.

-------------

1. Create the below **environment variables** 

Key  | Value
------------- | -------------
JKEAPP_DOCKER_REGISTRY  | hubcluster.icp:8500
JKEAPP_NAMESPACE | jke-app

These properties will be used to form the image names like **hubcluster.icp:8500/jke-app/jke-web**

![Sample Screen shot](images/01-jenkins-env-variables.png?raw=true "Sample Screen shot")

2. Create global credentials for **docker registry**

Key  | Value
------------- | -------------
Username  | admin
Password | --> docker registry password
Id | jke-credentials

![Sample Screen shot](images/02-jenkins-global-credentials.png?raw=true "Sample Screen shot")

3. Create global credentials for **git hub**

Key  | Value
------------- | -------------
Username  | ---> github.ibm account name
Password | --> git password token
Id | github-ibm

3. Create CICD pipeline for **jke-web**

Click on the appropriate menu for creating a new CICD pipeline

Here are the list of values you may need to enter in the below steps.

Key  | Value
------------- | -------------
pipeline name  | jke-web
Poll SCM | H/15 * * * *
Repository URL | https://github.ibm.com/SI-Lab-Apps/JKE.git
Credentials | github-ibm
Script Path | 4-Microservices/JkeWeb/config/Jenkinsfile

![Sample Screen shot](images/03-jenkins-cicd-1.png?raw=true "Sample Screen shot")

![Sample Screen shot](images/04-jenkins-cicd-2.png?raw=true "Sample Screen shot")

Do the below steps in the window.
* Enter pipeline name
* Enter Poll SCM
* Choose Pipeline script from SCM option
* Choose SCM as Git
* Enter Repository URL
* Choose the credentials
* Enter script path

3. Create CICD pipeline for **jke-batch**

Do the same steps that was done for the above pipeline with the following properties. Only the modified properies are given.

Key  | Value
------------- | -------------
pipeline name  | jke-batch
Script Path | 4-Microservices/JkeMQ/config/Jenkinsfile


4. Create CICD pipeline for **jke-interest**

Do the same steps that was done for the above pipeline with the following properties. Only the modified properies are given.

Key  | Value
------------- | -------------
pipeline name  | jke-interest
Script Path | 4-Microservices/JkeInterest/config/Jenkinsfile


5. Create CICD pipeline for **jke-stock**

Do the same steps that was done for the above pipeline with the following properties. Only the modified properies are given.

Key  | Value
------------- | -------------
pipeline name  | jke-stock
Script Path | 4-Microservices/JkeStock/config/Jenkinsfile

-------------

Now you have configured all the nodes of all the MCM clusters to download the images from MCM hub docker registry.
