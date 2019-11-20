PROJECT_NAME=jke2
DOCKER_REPOSITORY=gandigit
DOCKER_REPOSITORY_REMOTE=gandigit
DOCKER_USER=gandigit
DOCKER_PASSWORD=ibmPass0
DOCKER_EMAIL=gandhi555@gmail.com

ROOT_FOLDER=$(pwd)
ROOT_FOLDER_API=./../../JkeAPI
ROOT_FOLDER_MQ=./../../JkeMQ
ROOT_FOLDER_WEB=./../../JkeWeb

PROXY_NODE=9.204.168.81
ENTRY_POINT_URL=http://$(PROXY_NODE):$(shell kubectl get svc istio-ingressgateway -n istio-system -o json | jq .spec.ports[0].nodePort)
ENTRY_POINT_URL_UI=http://$(PROXY_NODE):$(shell kubectl get svc istio-ingressgateway -n istio-system -o json | jq .spec.ports[0].nodePort)
# URL_SERVICE_API=http://$(PROXY_NODE):$(shell kubectl get svc istio-ingressgateway -n istio-system -o json | jq .spec.ports[0].nodePort)
# URL_SERVICE_MQ=http://$(PROXY_NODE):$(shell kubectl get svc istio-ingressgateway -n istio-system -o json | jq .spec.ports[0].nodePort)
URL_SERVICE_API=http://$(PROJECT_NAME)-api-service
URL_SERVICE_MQ=http://$(PROJECT_NAME)-mq-service

DOCKER_IMAGE_API=$(DOCKER_REPOSITORY)/$(PROJECT_NAME)api
DOCKER_IMAGE_MQ=$(DOCKER_REPOSITORY)/$(PROJECT_NAME)mq
DOCKER_IMAGE_WEB=$(DOCKER_REPOSITORY)/$(PROJECT_NAME)web

FORTIO_POD=$(shell kubectl get pod -n $(PROJECT_NAME) -l app=fortio -o jsonpath='{.items[0].metadata.name}')
DEBUG_POD=$(shell kubectl get pod -n $(PROJECT_NAME) -l app=curl -o jsonpath='{.items[0].metadata.name}')

init:
	# -bash -c 'kubectl create namespace $(PROJECT_NAME)'
	-bash -c 'kubectl create secret docker-registry $(PROJECT_NAME)-secret --docker-server=docker.io --docker-username= $(DOCKER_USER) --docker-password= $(DOCKER_PASSWORD) --docker-email= $(DOCKER_EMAIL) -n default'

	# -bash -c 'kubectl label namespace $(PROJECT_NAME) istio-injection=enabled'
	# -sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' ./imagepolicy.yaml | kubectl apply -f -
	# -sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' ./debug.yaml | kubectl apply -f -
	# -sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' ./psp.yaml | kubectl apply -f -
	# -sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' ./ingress.yaml | kubectl apply -f -
	# -sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' ./config/fortio-deploy.yaml | kubectl apply -f -
	# -sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' ./destinationrules.yaml | kubectl apply -f -

clean:
	# -sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' ./destinationrules.yaml | kubectl delete -f -
	# -sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' ./debug.yaml | kubectl delete -f -
	# -sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' ./config/fortio-deploy.yaml | kubectl delete -f -
	# -sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' ./ingress.yaml | kubectl delete -f -
	# -sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' ./psp.yaml | kubectl delete -f -
	# -sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' ./imagepolicy.yaml | kubectl delete -f -
	-bash -c 'kubectl delete secret docker-registry $(PROJECT_NAME)-secret'
	-bash -c 'kubectl delete ns $(PROJECT_NAME)'

init-app-api:
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_API)/config/service.yaml | kubectl apply -f -

build-app-api:
	-cd $(ROOT_FOLDER_API); ./gradlew build

	-ls -l $(ROOT_FOLDER_API)
	-ls -l $(ROOT_FOLDER_API)/build/libs
	-docker build -f $(ROOT_FOLDER_API)/config/Dockerfile -t $(DOCKER_IMAGE_API):latest $(ROOT_FOLDER_API)

push-app-api:
	-docker login -u $(DOCKER_USER) -p $(DOCKER_PASSWORD)
	-docker push $(DOCKER_IMAGE_API):latest

deploy-app-api:
	-sed -e 's~<DOCKER_IMAGE>~$(DOCKER_IMAGE_API):latest~g' $(ROOT_FOLDER_API)/config/deployment.yaml  | sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' | kubectl apply -f -

deploy1:
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_API)/config/service.yaml
	-sed -e 's~<DOCKER_IMAGE>~$(DOCKER_IMAGE_API):latest~g' $(ROOT_FOLDER_API)/config/deployment.yaml
	-sed -e 's~<DOCKER_IMAGE>~$(DOCKER_IMAGE_API):latest~g' $(ROOT_FOLDER_API)/config/deployment.yaml  | sed -e 's~<PROJECT>~$(PROJECT_NAME)~g'

clean-app-api:
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_API)/config/service.yaml | kubectl delete -f -
	-sed -e 's~<DOCKER_IMAGE>~$(DOCKER_IMAGE_API):latest~g' $(ROOT_FOLDER_API)/config/deployment.yaml | sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' | kubectl delete -f -

	-docker stop $(DOCKER_IMAGE_API):latest
	-docker rm $(DOCKER_IMAGE_API):latest
	-docker rmi $(DOCKER_IMAGE_API):latest

test-app-api:
	-bash -c '$(ROOT_FOLDER_API)/config/test.sh $(ENTRY_POINT_URL)'

init-app-mq:
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_MQ)/config/service.yaml | kubectl apply -f -

build-app-mq:
	-cd $(ROOT_FOLDER_MQ); ./gradlew build
	-docker build -f $(ROOT_FOLDER_MQ)/config/Dockerfile -t $(DOCKER_IMAGE_MQ):latest $(ROOT_FOLDER_MQ)

push-app-mq:
	-docker login -u $(DOCKER_USER) -p $(DOCKER_PASSWORD)
	-docker push $(DOCKER_IMAGE_MQ):latest

deploy-app-mq:
	-sed -e 's~<DOCKER_IMAGE>~$(DOCKER_IMAGE_MQ):latest~g' $(ROOT_FOLDER_MQ)/config/deployment.yaml  | sed -e 's~<PROJECT>~$(PROJECT_NAME)~g'  | kubectl apply -f -

clean-app-mq:
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_MQ)/config/service.yaml | kubectl delete -f -
	-sed -e 's~<DOCKER_IMAGE>~$(DOCKER_IMAGE_MQ):latest~g'  $(ROOT_FOLDER_MQ)/config/deployment.yaml | sed -e 's~<PROJECT>~$(PROJECT_NAME)~g'  | kubectl delete -f -

	-docker stop $(DOCKER_IMAGE_MQ):latest
	-docker rm $(DOCKER_IMAGE_MQ):latest
	-docker rmi $(DOCKER_IMAGE_MQ):latest

test-app-mq:
	-bash -c '$(ROOT_FOLDER_MQ)/config/test.sh $(ENTRY_POINT_URL)'

init-app-web:
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_WEB)/config/service.yaml | kubectl apply -f -

build-app-web:
	-cd $(ROOT_FOLDER_WEB); ./gradlew build
	-docker build -f $(ROOT_FOLDER_WEB)/config/Dockerfile -t $(DOCKER_IMAGE_WEB):latest $(ROOT_FOLDER_WEB)

push-app-web:
	-docker login -u $(DOCKER_USER) -p $(DOCKER_PASSWORD)
	-docker push $(DOCKER_IMAGE_WEB):latest

deploy-app-web:
	-sed -e 's~<DOCKER_IMAGE>~$(DOCKER_IMAGE_WEB):latest~g' $(ROOT_FOLDER_WEB)/config/deployment.yaml  | sed -e 's~<PROJECT>~$(PROJECT_NAME)~g'  | kubectl apply -f -

clean-app-web:
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_WEB)/config/service.yaml | kubectl delete -f -
	-sed -e 's~<DOCKER_IMAGE>~$(DOCKER_IMAGE_WEB):latest~g' $(ROOT_FOLDER_WEB)/config/deployment.yaml | sed -e 's~<PROJECT>~$(PROJECT_NAME)~g'  | kubectl delete -f -

	-docker stop $(DOCKER_IMAGE_WEB):latest
	-docker rm $(DOCKER_IMAGE_WEB):latest
	-docker rmi $(DOCKER_IMAGE_WEB):latest

test-app-web:
	-bash -c '$(ROOT_FOLDER_WEB)/config/test.sh $(ENTRY_POINT_URL)'

init-all: init init-app-api init-app-mq init-app-web
build-all: build-app-api build-app-mq build-app-web
push-all: push-app-api push-app-mq push-app-web
deploy-all: deploy-app-api deploy-app-mq deploy-app-web
clean-all: clean clean-app-api clean-app-mq clean-app-web
test-all: test-app-api test-app-mq test-app-web

init-deploy-api: init init init-app-api build-app-api push-app-api deploy-app-api
init-deploy-mq: init init-app-mq build-app-mq push-app-mq deploy-app-mq
init-deploy-web: init init-app-web build-app-web push-app-web deploy-app-web

init-deploy-all: init-deploy-api init-deploy-mq init-deploy-web

initnp:
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_API)/config/networkpolicy.yaml | kubectl apply -f -
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_MQ)/config/networkpolicy.yaml | kubectl apply -f -
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_WEB)/config/networkpolicy.yaml | kubectl apply -f -

cleannp:
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_API)/config/networkpolicy.yaml | kubectl delete -f -
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_MQ)/config/networkpolicy.yaml | kubectl delete -f -
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_WEB)/config/networkpolicy.yaml | kubectl delete -f -


WEB_POD_NAME=$(shell kubectl -n $(PROJECT_NAME) get pod -l app=$(PROJECT_NAME)-web-service -o jsonpath='{.items[0].metadata.name}')

WEB_access:
	-$(shell kubectl -n $(PROJECT_NAME) port-forward $(WEB_POD_NAME) 9080:80)

restart-all:
	-kubectl delete pods --all -n $(PROJECT_NAME)
