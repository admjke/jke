PROJECT_NAME=jke1
DOCKER_REPOSITORY=gandigit
DOCKER_REPOSITORY_REMOTE=gandigit
DOCKER_USER=gandigit
DOCKER_PASSWORD=ibmPass0

ROOT_FOLDER=$(pwd)
ROOT_FOLDER_CATALOG=./../../catalogservice
ROOT_FOLDER_ORDER=./../../orderservice
ROOT_FOLDER_WEB=./../../ordproc-web

PROXY_NODE=9.204.168.81
ENTRY_POINT_URL=http://$(PROXY_NODE):$(shell kubectl get svc istio-ingressgateway -n istio-system -o json | jq .spec.ports[0].nodePort)
ENTRY_POINT_URL_UI=http://$(PROXY_NODE):$(shell kubectl get svc istio-ingressgateway -n istio-system -o json | jq .spec.ports[0].nodePort)
# URL_SERVICE_CATALOG=http://$(PROXY_NODE):$(shell kubectl get svc istio-ingressgateway -n istio-system -o json | jq .spec.ports[0].nodePort)
# URL_SERVICE_ORDER=http://$(PROXY_NODE):$(shell kubectl get svc istio-ingressgateway -n istio-system -o json | jq .spec.ports[0].nodePort)
URL_SERVICE_CATALOG=http://$(PROJECT_NAME)-catalog-service
URL_SERVICE_ORDER=http://$(PROJECT_NAME)-order-service

DOCKER_IMAGE_CATALOG=$(DOCKER_REPOSITORY)/$(PROJECT_NAME)catalog
DOCKER_IMAGE_ORDER=$(DOCKER_REPOSITORY)/$(PROJECT_NAME)order
DOCKER_IMAGE_WEB=$(DOCKER_REPOSITORY)/$(PROJECT_NAME)web

FORTIO_POD=$(shell kubectl get pod -n $(PROJECT_NAME) -l app=fortio -o jsonpath='{.items[0].metadata.name}')
DEBUG_POD=$(shell kubectl get pod -n $(PROJECT_NAME) -l app=curl -o jsonpath='{.items[0].metadata.name}')

init:
	-bash -c 'kubectl apply ns $(PROJECT_NAME)'
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
	-bash -c 'kubectl delete ns $(PROJECT_NAME)'

get-docker-password:
	-echo $(DOCKER_PASSWORD)
	-read -s -p "Enter Docker Password: " DOCKER_PASSWORD
	-echo $(DOCKER_PASSWORD)

init-app-catalog:
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_CATALOG)/config/service.yaml | kubectl apply -f -

build-app-catalog:
	-cd $(ROOT_FOLDER_CATALOG); ./gradlew build

	-ls -l $(ROOT_FOLDER_CATALOG)
	-ls -l $(ROOT_FOLDER_CATALOG)/build/libs
	-docker build -t $(DOCKER_IMAGE_CATALOG):latest $(ROOT_FOLDER_CATALOG)

push-app-catalog:
	-docker login -u $(DOCKER_USER) -p $(DOCKER_PASSWORD)
	-docker push $(DOCKER_IMAGE_CATALOG):latest

deploy-app-catalog:
	-sed -e 's~<DOCKER_IMAGE>~$(DOCKER_IMAGE_CATALOG):latest~g' $(ROOT_FOLDER_CATALOG)/config/deployment.yaml  | sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' | kubectl apply -f -

deploy1:
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_CATALOG)/config/service.yaml
	-sed -e 's~<DOCKER_IMAGE>~$(DOCKER_IMAGE_CATALOG):latest~g' $(ROOT_FOLDER_CATALOG)/config/deployment.yaml
	-sed -e 's~<DOCKER_IMAGE>~$(DOCKER_IMAGE_CATALOG):latest~g' $(ROOT_FOLDER_CATALOG)/config/deployment.yaml  | sed -e 's~<PROJECT>~$(PROJECT_NAME)~g'

clean-app-catalog:
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_CATALOG)/config/service.yaml | kubectl delete -f -
	-sed -e 's~<DOCKER_IMAGE>~$(DOCKER_IMAGE_CATALOG):latest~g' $(ROOT_FOLDER_CATALOG)/config/deployment.yaml | sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' | kubectl delete -f -

	-docker stop $(DOCKER_IMAGE_CATALOG):latest
	-docker rm $(DOCKER_IMAGE_CATALOG):latest
	-docker rmi $(DOCKER_IMAGE_CATALOG):latest

test-app-catalog:
	-bash -c '$(ROOT_FOLDER_CATALOG)/config/test.sh $(ENTRY_POINT_URL)'


init-app-order:
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_ORDER)/config/service.yaml | kubectl apply -f -

build-app-order:
	-cd $(ROOT_FOLDER_ORDER); ./gradlew build
	-docker build -t $(DOCKER_IMAGE_ORDER):latest $(ROOT_FOLDER_ORDER)

push-app-order:
	-docker login -u $(DOCKER_USER) -p $(DOCKER_PASSWORD)
	-docker push $(DOCKER_IMAGE_ORDER):latest

deploy-app-order:
	-sed -e 's~<DOCKER_IMAGE>~$(DOCKER_IMAGE_ORDER):latest~g' $(ROOT_FOLDER_ORDER)/config/deployment.yaml  | sed -e 's~<PROJECT>~$(PROJECT_NAME)~g'  | kubectl apply -f -

clean-app-order:
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_ORDER)/config/service.yaml | kubectl delete -f -
	-sed -e 's~<DOCKER_IMAGE>~$(DOCKER_IMAGE_ORDER):latest~g'  $(ROOT_FOLDER_ORDER)/config/deployment.yaml | sed -e 's~<PROJECT>~$(PROJECT_NAME)~g'  | kubectl delete -f -

	-docker stop $(DOCKER_IMAGE_ORDER):latest
	-docker rm $(DOCKER_IMAGE_ORDER):latest
	-docker rmi $(DOCKER_IMAGE_ORDER):latest

test-app-order:
	-bash -c '$(ROOT_FOLDER_ORDER)/config/test.sh $(ENTRY_POINT_URL)'

init-app-web:
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_WEB)/config/service.yaml | kubectl apply -f -
	-sed -e 's~<URL_SERVICE_CATALOG>~$(URL_SERVICE_CATALOG)~g' $(ROOT_FOLDER_WEB)/angularUI/src/environments/environment.ts.template > $(ROOT_FOLDER_WEB)/angularUI/src/environments/environment.ts.template1
	-sed -e 's~<ORDER_SERVICE_URL>~$(URL_SERVICE_ORDER)~g' $(ROOT_FOLDER_WEB)/angularUI/src/environments/environment.ts.template1 > $(ROOT_FOLDER_WEB)/angularUI/src/environments/environment.ts
	-sed -e 's~<URL_SERVICE_CATALOG>~$(URL_SERVICE_CATALOG)~g' $(ROOT_FOLDER_WEB)/angularUI/src/environments/environment.prod.ts.template > $(ROOT_FOLDER_WEB)/angularUI/src/environments/environment.prod.ts.template1
	-sed -e 's~<ORDER_SERVICE_URL>~$(URL_SERVICE_ORDER)~g' $(ROOT_FOLDER_WEB)/angularUI/src/environments/environment.prod.ts.template1 > $(ROOT_FOLDER_WEB)/angularUI/src/environments/environment.prod.ts

build-app-web:
	-cd $(ROOT_FOLDER_WEB)/angularUI; ng build
	-cp -rf $(ROOT_FOLDER_WEB)/angularUI/dist/ordproc-ui/* $(ROOT_FOLDER_WEB)/src/main/resources/static

	# log to display the new file content
	-ls -l $(ROOT_FOLDER_WEB)/src/main/resources/static
	-cat $(ROOT_FOLDER_WEB)/src/main/resources/static/main.js | grep catalogServiceURL

	-cd $(ROOT_FOLDER_WEB); ./gradlew build
	-ls -l $(ROOT_FOLDER_WEB)/build/libs
	-docker build -t $(DOCKER_IMAGE_WEB):latest $(ROOT_FOLDER_WEB)

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

init-all: init init-app-catalog init-app-order init-app-web
build-all: build-app-catalog build-app-order build-app-web
push-all: push-app-catalog push-app-order push-app-web
deploy-all: deploy-app-catalog deploy-app-order deploy-app-web
clean-all: clean clean-app-catalog clean-app-order clean-app-web
test-all: test-app-catalog test-app-order test-app-web

init-deploy-catalog: init init-app-catalog build-app-catalog push-app-catalog deploy-app-catalog
init-deploy-order: init-app-order build-app-order push-app-order deploy-app-order
init-deploy-web: init-app-web build-app-web push-app-web deploy-app-web

init-deploy-all: init-deploy-catalog init-deploy-order init-deploy-web

initnp:
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_CATALOG)/config/networkpolicy.yaml | kubectl apply -f -
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_ORDER)/config/networkpolicy.yaml | kubectl apply -f -
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_WEB)/config/networkpolicy.yaml | kubectl apply -f -

cleannp:
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_CATALOG)/config/networkpolicy.yaml | kubectl delete -f -
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_ORDER)/config/networkpolicy.yaml | kubectl delete -f -
	-sed -e 's~<PROJECT>~$(PROJECT_NAME)~g' $(ROOT_FOLDER_WEB)/config/networkpolicy.yaml | kubectl delete -f -


WEB_POD_NAME=$(shell kubectl -n $(PROJECT_NAME) get pod -l app=$(PROJECT_NAME)-web-service -o jsonpath='{.items[0].metadata.name}')

WEB_access:
	$(shell kubectl -n $(PROJECT_NAME) port-forward $(WEB_POD_NAME) 9080:80)

restart-all:
	kubectl delete pods --all -n $(PROJECT_NAME)
