# Linking MCM Hub Private Docker Registry to its clusters

Here we will see how to configure MCM clusters to download images from MCM Hub private docker repository.

## Scenario 1 : MCM hub is on ICP and MCM Clusters are also in ICP.

Lets assume that we have the following

* 1 MCM Hub
* 3 MCM clusters

Key  | Values
------------- | -------------
MCM hub IP Address  | 9.20.194.93
MCM hub docker registry | hubcluster.icp:8500


Now you have to do the below steps in **all the nodes of the MCM clusters (masters and workers)**.

-------------

1. Make the below entry in **/etc/hosts** file (of the all the nodes in the clusters).

```
9.20.194.93 hubcluster.icp
```

2. Create the below directory (in all the nodes in the clusters).

```
mkdir /etc/docker/certs.d/hubcluster.icp:8500
```

3. Copy the **ca.crt** from MCM hub (to all the nodes in the clusters).

```
scp root@9.20.194.93:/etc/docker/certs.d/hubcluster.icp:8500/ca.crt /etc/docker/certs.d/hubcluster.icp:8500/ca.crt
```

It may ask for the root password of the MCM hub, while copying.

-------------

Now you have configured all the nodes of all the MCM clusters to download the images from MCM hub docker registry.
